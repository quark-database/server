package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.TableRecordFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class LinearRecordCollection extends RecordCollection {
    private final ArrayList<TableRecord> records = new ArrayList<>();

    @Override
    public Iterator<TableRecord> iterator() {
        return records.iterator();
    }

    @Override
    public ArrayList<TableRecord> toList() {
        return new ArrayList<>(records);
    }

    @Override
    public void add(TableRecord record) {
        records.add(record);
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
        var iterator = iterator();

        while(iterator.hasNext() && limiter.fitsTheLimit()) {
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

    @Override
    public int count() {
        return records.size();
    }

    @Override
    public Optional<TableRecord> find(TableRecordFinder finder) {
        for(var record : records) {
            if(finder.apply(record)) {
                return Optional.of(record);
            }
        }

        return Optional.empty();
    }

    @Override
    public void exclude(TableRecordFinder finder) {
        remove(finder, RecordIterationLimiter.UNLIMITED);
    }
}
