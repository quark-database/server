package ru.anafro.quark.server.databases.data.structures;

import ru.anafro.quark.server.databases.data.RecordIterationLimiter;
import ru.anafro.quark.server.databases.data.RecordLambda;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.TableRecordFinder;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
    public abstract Optional<TableRecord> find(TableRecordFinder finder);

    /**
     * @since Quark 1.2
     * @param finder
     */
    public abstract void exclude(TableRecordFinder finder);

    public abstract ArrayList<TableRecord> toList();

    @Override
    public String toString() {
        var buffer = new TextBuffer();

        for(var record : this) {
            buffer.appendLine(record);
        }

        return buffer.extractContent();
    }

    @Override
    public Iterator<TableRecord> iterator() {
        return toList().iterator();
    }
}
