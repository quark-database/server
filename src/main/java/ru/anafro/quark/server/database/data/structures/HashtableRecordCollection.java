package ru.anafro.quark.server.database.data.structures;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.RecordIterationLimiter;
import ru.anafro.quark.server.database.data.RecordLambda;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.integers.Integers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

public class HashtableRecordCollection extends RecordCollection {
    public final int HASHTABLE_SIZE = 16;
    private final String keyColumn;
    private final HashtableRecordCollectionChain[] recordChains;

    public HashtableRecordCollection(String keyColumn) {
        this.keyColumn = keyColumn;
        this.recordChains = new HashtableRecordCollectionChain[HASHTABLE_SIZE];

        for (int index = 0; index < recordChains.length; index++) {
            this.recordChains[index] = new HashtableRecordCollectionChain();
        }
    }

    @Override
    public void add(TableRecord record) {
        chainAt(Integers.positiveModulus(record.getField(keyColumn).getEntity().hashCode(), recordChains.length)).add(record);
    }

    @Override
    public RecordCollection select(Function<TableRecord, Boolean> selectionCondition, RecordIterationLimiter limiter) {
        var collection = new LinearRecordCollection();

        for (var record : this) {
            if (limiter.fitsTheLimit()) {
                if (limiter.isSkipNeeded()) {
                    limiter.skipped();
                } else if (selectionCondition.apply(record)) {
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
        while (limiter.fitsTheLimit()) {
            for (var chain : recordChains) {
                var iterator = chain.iterator();

                while (iterator().hasNext() && limiter.fitsTheLimit()) {
                    var record = iterator.next();

                    if (selectionCondition.apply(record)) {
                        if (limiter.isSkipNeeded()) {
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

        for (var chain : recordChains) {
            count += chain.getRecords().size();
        }

        return count;
    }

    @Override
    public Optional<TableRecord> find(TableRecordFinder finder) {
        var chain = chainAt(Integers.positiveModulus(finder.findingValue().hashCode(), HASHTABLE_SIZE));

        for (var record : chain) {
            if (finder.apply(record)) {
                return Optional.of(record);
            }
        }

        return Optional.empty();
    }

    @Override
    public void exclude(TableRecordFinder finder) {
        var chain = chainAt(Integers.positiveModulus(finder.findingValue().hashCode(), HASHTABLE_SIZE));
        chain.getRecords().removeIf(record -> record.getField(finder.columnName()).getEntity().equals(finder.findingValue()));
    }

    @Override
    public ArrayList<TableRecord> toList() {
        var list = Lists.<TableRecord>empty();

        for (var chain : recordChains) {
            list.addAll(chain.getRecords());
        }

        return list;
    }

    public HashtableRecordCollectionChain[] getRecordChains() {
        return recordChains;
    }

    public HashtableRecordCollectionChain chainAt(int index) {
        return recordChains[index];
    }

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

        @NotNull
        @Override
        public Iterator<TableRecord> iterator() {
            return records.iterator();
        }
    }
}
