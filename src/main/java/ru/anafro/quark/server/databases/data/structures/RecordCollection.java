package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;

import java.util.List;

public abstract class RecordCollection implements Iterable<TableRecord> {
    public RecordCollection() {
        //
    }

    public RecordCollection(Iterable<? extends TableRecord> records) {
        addAll(records);
    }

    public RecordCollection(TableRecord... records) {
        addAll(List.of(records));
    }

    public abstract void add(TableRecord record);

    public void addAll(Iterable<? extends TableRecord> records) {
        for(var record : records) {
            add(record);
        }
    }

    public abstract RecordCollection select(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter);
    public abstract void remove(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter);

    public abstract int count();
}
