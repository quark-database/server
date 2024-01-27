package ru.anafro.quark.server.utils.collections;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static ru.anafro.quark.server.utils.collections.Lists.empty;

public class BTree<K extends Comparable<K>, V> implements Iterable<V> {
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static final int CHILDREN_LIMIT = 4;

    private Node root;       // root of the B-tree
    private int height;      // height of the B-tree
    private int size;           // number of key-value pairs in the B-tree

    /**
     * Initializes an empty B-tree.
     */
    public BTree() {
        root = new Node(0);
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        return search(root, key, height);
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to put() is null");
        }

        Node u = insert(root, key, value, height);
        size += 1;

        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.getChildren().set(0, new Entry(root.getChildren().getFirst().key, null, root));
        t.getChildren().set(1, new Entry(u.getChildren().getFirst().key, null, u));
        root = t;
        height++;
    }

    public void remove(K key) {
        var entry = entrySearch(root, key, height);

        if (entry != null) {
            entry.value = null;
        }
    }

    private Entry entrySearch(Node x, K key, int ht) {
        var children = x.getChildren();

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.getChildrenCount(); j++) {
                if (eq(key, children.get(j).getKey())) return children.get(j);
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.getChildrenCount(); j++) {
                if (j + 1 == x.getChildrenCount() || less(key, children.get(j + 1).getKey())) {
                    return entrySearch(children.get(j).getNext(), key, ht - 1);
                }
            }
        }
        return null;
    }

    private V search(Node x, K key, int ht) {
        var entry = entrySearch(x, key, ht);
        return entry == null ? null : entry.value;
    }

    private Node insert(Node target, K key, V value, int height) {
        int index;
        Entry entry = new Entry(key, value, null);

        if (height == 0) {  // external node
            for (index = 0; index < target.getChildrenCount(); index++) {
                if (less(key, target.getChildren().get(index).key)) {
                    break;
                }
            }
        } else {  // internal node
            for (index = 0; index < target.getChildrenCount(); index++) {
                if (index + 1 == target.getChildrenCount() || less(key, target.getChildren().get(index + 1).key)) {
                    Node inserted = insert(target.getChildren().get(index++).next, key, value, height - 1);

                    if (inserted == null) {
                        return null;
                    }

                    entry.setKey(inserted.getChildren().getFirst().key);
                    entry.value = null;
                    entry.setNext(inserted);

                    break;
                }
            }
        }

        for (int i = target.getChildrenCount(); i > index; i--) {
            target.getChildren().set(i, target.getChildren().get(i - 1));
        }

        target.getChildren().set(index, entry);
        target.childrenCount = target.getChildrenCount() + 1;

        if (target.getChildrenCount() < CHILDREN_LIMIT) {
            return null;
        } else {
            return split(target);
        }
    }

    // split node in half
    private Node split(Node target) {
        Node node = new Node(CHILDREN_LIMIT / 2);
        target.childrenCount = CHILDREN_LIMIT / 2;

        for (var i = 0; i < target.childrenCount; i += 1) {
            node.getChildren().add(target.getChildren().removeLast());
        }

        return node;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return STR."\{toString(root, height, "")}\n";
    }

    private String toString(Node currentNode, int height, String indent) {
        var buffer = new TextBuffer();
        var children = currentNode.getChildren();

        if (height == 0) {
            for (var child : children) {
                buffer.appendLine(indent, child.getKey(), " ", child.value);
            }
        } else {
            for (int i = 0; i < currentNode.getChildrenCount(); i++) {
                var child = children.get(i);

                if (i > 0) {
                    buffer.append(STR."\{indent}(\{child.getKey()})\n");
                }

                buffer.append(toString(child.getNext(), height - 1, STR."\{indent}     "));
            }
        }

        return buffer.toString();
    }

    public void traverse(Consumer<V> consumer) {
        if (root != null) {
            root.traverse(consumer);
        }
    }

    public <A> A accumulate(A accumulator, BiConsumer<V, A> accumulationAction) {
        traverse(value -> accumulationAction.accept(value, accumulator));
        return accumulator;
    }

    public ArrayList<V> toList() {
        return accumulate(empty(), (value, list) -> list.add(value));
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {  // TODO: Implement a BTree Iterator.
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                int index = 0;
                return index < size();
            }

            @Override
            public V next() {
                return null;
            }
        };
    }

    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(K firstKey, K secondKey) {
        return firstKey.compareTo(secondKey) < 0;
    }

    private boolean eq(K firstKey, K secondKey) {
        return firstKey.compareTo(secondKey) == 0;
    }

    // helper B-tree node json type
    public final class Node {
        private final ArrayList<Entry> children = new ArrayList<>(4);   // the array of children
        private int childrenCount;                             // number of children

        private Node(int childrenCount) {
            this.childrenCount = childrenCount;
        }

        public ArrayList<Entry> getChildren() {
            return children;
        }

        public int getChildrenCount() {
            return childrenCount;
        }

        public void traverse(Consumer<V> consumer) {
            for (int i = 0; i < getChildrenCount(); i++) {
                Lists.tryGet(getChildren(), i).ifPresent(child -> {
                    if (child.getNext() != null) {
                        child.getNext().traverse(consumer);
                    }

                    if (child.getValue() != null) {
                        consumer.accept(child.getValue());
                    }
                });

                // If this is not leaf, then before printing key[i],
                // traverse the subtree rooted with child C[i].

            }
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    public class Entry {
        public V value;
        private K key;
        private Node next;     // helper field to iterate over array entries

        public Entry(K key, V value, Node next) {
            this.setKey(key);
            this.value = value;
            this.setNext(next);
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
