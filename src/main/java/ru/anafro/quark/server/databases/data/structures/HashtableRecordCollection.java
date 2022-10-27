package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.TableRecordFinder;
import ru.anafro.quark.server.utils.integers.Integers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class HashtableRecordCollection extends RecordCollection {
    private final String keyColumn;
    public final int HASHTABLE_SIZE = 256;
    private final HashtableRecordCollectionChain[] recordChains;

    public static class HashtableRecordCollectionChain implements Iterable<TableRecord> {
        private final ArrayList<TableRecord> records;

        public HashtableRecordCollectionChain() {
            this.records = new ArrayList<>();
        }

        public void add(TableRecord record) {
            records.add(record);
        }

        public ArrayList<TableRecord> getRecords() {
            return records;
        }

        @Override
        public Iterator<TableRecord> iterator() {
            return records.iterator();
        }
    }

    public static class HashtableRecordCollectionIterator implements Iterator<TableRecord> {
        private final HashtableRecordCollection hashtable;
        private int currentChain = 0;
        private int currentIndex = 0;

        public HashtableRecordCollectionIterator(HashtableRecordCollection hashtable) {
            this.hashtable = hashtable;
        }

        @Override
        public boolean hasNext() {
            return currentChain >= hashtable.getRecordChains().length;
        }

        @Override
        public TableRecord next() {
            var record = hashtable.chainAt(currentChain).getRecords().get(currentIndex++);

            if(currentIndex >= hashtable.chainAt(currentChain).getRecords().size()) {
                currentChain += 1;
                currentIndex = 0;
            }

            return record;
        }

        public HashtableRecordCollection getHashtable() {
            return hashtable;
        }

        public int getCurrentChain() {
            return currentChain;
        }
    }

    public HashtableRecordCollection(String keyColumn) {
        this.keyColumn = keyColumn;
        this.recordChains = new HashtableRecordCollectionChain[HASHTABLE_SIZE];

        for(int index = 0; index < recordChains.length; index++) {
            this.recordChains[index] = new HashtableRecordCollectionChain();
        }
    }

    @Override
    public void add(TableRecord record) {
        chainAt(Integers.positiveModulus(record.getField(keyColumn).getValue().hashCode(), recordChains.length)).add(record);
    }

    @Override
    public RecordCollection select(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        var collection = new LinearRecordCollection();

        for(var record : this) {
            if(limiter.fitsTheLimit()) {
                if(limiter.isSkipNeeded()) {
                    limiter.skipped();
                } else if(selectionCondition.apply(record)) {
                    collection.add(record);
                    limiter.selected();
                }
            } else {
                break;
            }
        }

        return collection;
    }

    @Override
    public void remove(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        while(limiter.fitsTheLimit()) {
            for(var chain : recordChains) {
                var iterator = chain.iterator();

                while(iterator().hasNext() && limiter.fitsTheLimit()) {
                    var record = iterator.next();

                    if(selectionCondition.apply(record)) {
                        if(limiter.isSkipNeeded()) {
                            limiter.skipped();
                        } else {
                            iterator.remove();
                            limiter.selected();
                        }
                    }
                }
            }
        }
    }

    @Override
    public int count() {
        int count = 0;

        for(var chain : recordChains) {
            count += chain.getRecords().size();
        }

        return count;
    }

    @Override
    public Optional<TableRecord> find(TableRecordFinder finder) {
        var chain = chainAt(Integers.positiveModulus(finder.getFindingValue().hashCode(), HASHTABLE_SIZE));

        for(var record : chain) {
            if(finder.apply(record)) {
                return Optional.of(record);
            }
        }

        return Optional.empty();
    }

    @Override
    public Iterator<TableRecord> iterator() {
        return new HashtableRecordCollectionIterator(this);
    }

    public HashtableRecordCollectionChain[] getRecordChains() {
        return recordChains;
    }

    public HashtableRecordCollectionChain chainAt(int index) {
        return recordChains[index];
    }
}
