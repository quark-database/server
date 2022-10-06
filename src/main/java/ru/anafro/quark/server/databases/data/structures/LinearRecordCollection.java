package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;

public class LinearRecordCollection extends RecordCollection {
    private final ArrayList<TableRecord> records = new ArrayList<>();

    @Override
    public Iterator<TableRecord> iterator() {
        return records.iterator();
    }

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
}
