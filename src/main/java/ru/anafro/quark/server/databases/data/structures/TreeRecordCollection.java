package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.TableRecordFinder;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.exceptions.NotImplementedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeRecordCollection extends RecordCollection {
    public static final int DEFAULT_PAGE_SIZE = 7;
    private final String keyColumn;
    private TreeRecordCollectionNode root;
    private final Logger logger = new Logger(getClass(), LogLevel.DEBUG);

    public static class TreeRecordCollectionNode {
        private final TreeRecordCollection owner;
        private final TableRecord[] records;
        private final TreeRecordCollectionNode[] children;
        private final int degree;
        private final boolean isLeaf;
        private int size;

        public TreeRecordCollectionNode(TreeRecordCollection owner, int degree, boolean isLeaf) {
            this.owner = owner;
            this.degree = degree;
            this.isLeaf = isLeaf;

            this.records = new TableRecord[2 * degree - 1];
            this.children = new TreeRecordCollectionNode[2 * degree];
            this.size = 0;
        }

        public void insertNonFull(TableRecord record) {
            var rightmost = size - 1;

            if(this.isLeaf()) {
                while(rightmost >= 0 && recordAt(rightmost).getField(owner.getKeyColumn()).getValue().compareTo(record.getField(owner.getKeyColumn()).getValue()) > 0) {
                    records[rightmost + 1] = recordAt(rightmost);
                    rightmost -= 1;
                }

                records[rightmost + 1] = record;
                size += 1;
            } else {
                while(rightmost >= 0 && recordAt(rightmost).getField(owner.getKeyColumn()).getValue().compareTo(record.getField(owner.getKeyColumn()).getValue()) > 0) {
                    rightmost -= 1;
                }

                if(childAt(rightmost + 1).getSize() == children.length - 1) {
                    splitChild(rightmost + 1, childAt(rightmost + 1));

                    if(recordAt(rightmost + 1).getField(owner.getKeyColumn()).getValue().compareTo(record.getField(owner.getKeyColumn()).getValue()) < 0) {
                        rightmost += 1;
                    }
                }

                childAt(rightmost + 1).insertNonFull(record);
            }
        }

        public int findKey(TableRecord record) {
            int index = 0;

            while(index < size && recordAt(index).getField(owner.keyColumn).getValue().compareTo(record.getField(owner.keyColumn).getValue()) < 0) {
                index += 1;
            }

            return index;
        }

        public void remove(TableRecord record) {
            int index = findKey(record);

            if(index < size && recordAt(index).getField(owner.keyColumn).getValue().compareTo(record.getField(owner.keyColumn).getValue()) == 0) {
                if(this.isLeaf()) {
                    removeFromLeaf(index);
                } else {
                    removeFromNonLeaf(index);
                }
            } else {
                if(this.isLeaf()) {
                    // No value present in this tree.
                    return;
                }

                boolean isKeyPresent = index == size;

                if(childAt(index).getSize() < degree) {
                    fill(index);
                }

                if(isKeyPresent && index > size) {
                    childAt(index - 1).remove(record);
                } else {
                    childAt(index).remove(record);
                }
            }
        }

        private void fill(int index) {
            if(index != 0 && childAt(index - 1).getSize() >= degree) {
                borrowFromPrevious(index);
            } else if(index != size && childAt(index + 1).getSize() >= index) {
                borrowFromNext(index);
            } else {
                if(index != size) {
                    merge(index);
                } else {
                    merge(index + 1);
                }
            }
        }

        private void borrowFromNext(int borrowingIndex) {
            var child = childAt(borrowingIndex);
            var sibling = childAt(borrowingIndex + 1);

            child.records[child.getSize()] = recordAt(borrowingIndex);

            if(child.isNotLeaf()) {
                child.children[child.getSize() + 1] = sibling.children[0];
            }

            records[borrowingIndex] = sibling.recordAt(0);

            for(int index = 1; index < sibling.getSize(); index++) {
                sibling.records[index - 1] = sibling.records[index];
            }

            if(sibling.isNotLeaf()) {
                for(int index = 1; index < sibling.getSize(); index++) {
                    sibling.children[index - 1] = sibling.children[index];
                }
            }

            child.size += 1;
            sibling.size -= 1;
        }

        private void borrowFromPrevious(int borrowingIndex) {
            var child = childAt(borrowingIndex);
            var sibling = childAt(borrowingIndex - 1);

            for(int index = child.getSize() - 1; index >= 0; index -= 1) {
                child.records[index + 1] = child.records[index];
            }

            if(child.isNotLeaf()) {
                for(int index = child.getSize(); index >= 0; index -= 1) {
                    child.children[index + 1] = child.children[index];
                }
            }

            child.records[0] = recordAt(borrowingIndex - 1);

            if(child.isNotLeaf()) {
                child.children[0] = sibling.children[sibling.getSize()];
            }

            records[borrowingIndex - 1] = sibling.recordAt(sibling.getSize() - 1);

            child.size += 1;
            sibling.size -= 1;
        }

        private void removeFromNonLeaf(int removingIndex) {
            var record = recordAt(removingIndex);

            if(childAt(removingIndex).getSize() >= degree) {
                var predecessor = getPredecessor(removingIndex);
                records[removingIndex] = predecessor;
                childAt(removingIndex).remove(predecessor);
            } else if(childAt(removingIndex + 1).getSize() >= degree) {
                var successor = getSuccessor(removingIndex);
                records[removingIndex] = successor;
                childAt(removingIndex + 1).remove(successor);
            } else {
                merge(removingIndex);
                childAt(removingIndex).remove(record);
            }
        }

        private void merge(int mergingIndex) {
            var child = childAt(mergingIndex);
            var sibling = childAt(mergingIndex + 1);

            child.records[degree - 1] = records[mergingIndex];

            if(sibling.getSize() >= 0) {
                System.arraycopy(sibling.records, 0, child.records, degree, sibling.getSize());
            }

            if(child.isNotLeaf()) {
                for(int index = 0; index < sibling.getSize(); index++) {
                    child.children[index + degree] = sibling.childAt(index);
                }
            }

            for(int index = mergingIndex + 1; index < size; index++) {
                records[index - 1] = records[index];
            }

            for(int index = mergingIndex + 2; index <= size; index++) {
                children[index - 1] = childAt(index);
            }

            child.size += sibling.size + 1;
            size -= 1;
        }

        private TableRecord getPredecessor(int index) {
            var current = childAt(index);

            while(current.isNotLeaf()) {
                current = current.childAt(current.getSize());
            }

            return current.recordAt(current.getSize() - 1);
        }

        private TableRecord getSuccessor(int index) {
            var current = childAt(index + 1);

            while(current.isNotLeaf()) {
                current = current.childAt(0);
            }

            return current.recordAt(0);
        }

        private void removeFromLeaf(int removingIndex) {
            for(int index = removingIndex + 1; index < size; index++) {
                records[index - 1] = records[index];
            }

            size -= 1;
        }

        public <T> void traverse(RecordLambda<T> consumer) {
            owner.logger.debug("Traversing: " + Arrays.stream(records).map(record -> record.getField(owner.getKeyColumn()).getValue().toInstructionForm()).collect(Collectors.joining(", ")));

            for(int index = 0; index < size; index++) {
                if(this.isNotLeaf()) {
                    owner.logger.debug("This block is not a leaf. Traversing: " + Arrays.stream(childAt(index).getRecords()).map(record -> record.getField(owner.getKeyColumn()).getValue().toInstructionForm()).collect(Collectors.joining(", ")));
                    childAt(index).traverse(consumer);
                }

                owner.logger.debug("Applying function to: " + recordAt(index).getField(owner.getKeyColumn()).getValue().toInstructionForm());
                consumer.apply(recordAt(index));
            }
        }

        public void splitChild(int childIndexBorder, TreeRecordCollectionNode child) {
            var storage = new TreeRecordCollectionNode(owner, child.getDegree(), child.isLeaf());
            storage.size = degree - 1;

            for(int index = 0; index < degree - 1; index++) {
                storage.records[index] = child.recordAt(index + degree);
            }

            if(child.isNotLeaf()) {
                if(degree >= 0) {
                    System.arraycopy(child.children, degree, storage.children, 0, degree);
                }
            }

            child.size = degree - 1;

            for(int index = size; index >= childIndexBorder + 1; index -= 1) {
                children[index + 1] = childAt(index);
            }

            children[childIndexBorder] = storage;

            for(int index = size - 1; index >= childIndexBorder; index -= 1) {
                records[index + 1] = recordAt(index);
            }

            records[childIndexBorder] = child.recordAt(degree - 1);

            size += 1;
        }

        public Optional<TreeRecordCollectionNode> find(TableRecordFinder finder) {
            var findingValue = finder.getFindingValue();

            for(int index = 0; index < size; index++) {
                var comparingValue = recordAt(index).getField(finder.getColumnName()).getValue();

                if(findingValue.compareTo(comparingValue) > 0) {
                    continue;
                }

                if(findingValue.compareTo(comparingValue) == 0) {
                    return Optional.of(this);
                }

                if(this.isLeaf()) {
                    return Optional.empty();
                }

                return childAt(index).find(finder);
            }

            Quark.warning("TreeRecordCollectionNode.find(finder) reached a code line that should not be reached. Optional.empty() is returned.");
            return Optional.empty();
        }

        public TableRecord[] getRecords() {
            return records;
        }

        public int getDegree() {
            return degree;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public boolean isNotLeaf() {
            return !isLeaf();
        }

        public TreeRecordCollectionNode childAt(int index) {
            return children[index];
        }

        public TableRecord recordAt(int index) {
            return records[index];
        }

        public TreeRecordCollection getOwner() {
            return owner;
        }

        public TreeRecordCollectionNode[] getChildren() {
            return children;
        }

        public int getSize() {
            return size;
        }

        public <T> void traverseNodes(Function<TreeRecordCollectionNode, T> function) {

        }
    }

    @Override
    public void add(TableRecord record) {
        logger.debug("Adding: " + record.getField(keyColumn).getValue().toInstructionForm());

        if(root == null) {
            logger.debug("Root is null");

            root = new TreeRecordCollectionNode(this, DEFAULT_PAGE_SIZE, true);
            logger.debug("Created a new root page node");

            root.records[0] = record;
            logger.debug("The first record in the root is set");

            root.size = 1;
            logger.debug("Size set to 1");
        } else {
            logger.debug("Root is not null");

            if(root.size == 2 * DEFAULT_PAGE_SIZE - 1) {
                logger.debug("root.size == 2 * DEFAULT_PAGE_SIZE - 1");

                logger.debug("Created a new node");
                var node = new TreeRecordCollectionNode(this, DEFAULT_PAGE_SIZE, false);

                logger.debug("Now the first child is root");
                node.children[0] = root;

                logger.debug("Split");
                node.splitChild(0, root);

                logger.debug("Finding a new index for insertion");
                int index = 0;
                while(index < node.records.length && node.records[index].getField(keyColumn).getValue().compareTo(record.getField(keyColumn).getValue()) < 0) {
                    index += 1;
                }

                logger.debug("Index = " + index);

                node.childAt(index).insertNonFull(record);
            } else {
                logger.debug("Inserting non full this record");
                root.insertNonFull(record);
            }
        }
    }

    public <T> void traverse(RecordLambda<T> consumer) {
        if(root != null) {
            root.traverse(consumer);
        }
    }

    public <T> void traverseNodes(Function<TreeRecordCollectionNode, T> function) {
        if(root != null) {
            root.traverseNodes(function);
        }
    }

    @Override
    public Optional<TableRecord> find(TableRecordFinder finder) {
        if(root == null) {
            return Optional.empty();
        }

        return root.find(finder).map(treeRecordCollectionNode -> treeRecordCollectionNode.getRecords()[0]);
    }

    public TreeRecordCollection(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    @Override
    public RecordCollection select(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        var records = new LinearRecordCollection();

        this.traverse(record -> {
            if(limiter.fitsTheLimit()) {
                if(limiter.isSkipNeeded()) {
                    limiter.skipped();
                } else if(selectionCondition.apply(record)) {
                    records.add(record);
                    limiter.selected();
                }
            }

            return null;
        });

        return records;
    }

    @Override
    public void remove(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        throw new UnsupportedOperationException("TreeRecordCollection.remove() must not be used.");
    }

    @Override
    public int count() {
        AtomicInteger count = new AtomicInteger();

        traverse(__ -> count.getAndIncrement());

        return count.get();
    }

    @Override
    public Iterator<TableRecord> iterator() {
        throw new NotImplementedException();
    }

    public TreeRecordCollectionNode getRoot() {
        return root;
    }

    public String getKeyColumn() {
        return keyColumn;
    }
}
