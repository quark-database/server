package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.utils.exceptions.NotImplementedException;
import ru.anafro.quark.server.utils.hashing.HashingFunction;

import java.util.Iterator;

public class HashMapRecordCollection extends RecordCollection {
    private HashingFunction<?> hashingFunction;

    public HashMapRecordCollection() {
        throw new NotImplementedException();
    }

    public static class Entry {
        // TODO
    }

    @Override
    public void add(TableRecord record) {
        throw new NotImplementedException();
    }

    @Override
    public RecordCollection select(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter) {
        throw new NotImplementedException();
    }

    @Override
    public int count() {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<TableRecord> iterator() {
        throw new NotImplementedException();
    }
}
