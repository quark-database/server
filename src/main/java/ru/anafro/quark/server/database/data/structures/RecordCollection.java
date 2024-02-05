package ru.anafro.quark.server.database.data.structures;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.RecordIterationLimiter;
import ru.anafro.quark.server.database.data.RecordLambda;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.language.entities.RecordEntity;
import ru.anafro.quark.server.utils.collections.Collections;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static ru.anafro.quark.server.utils.collections.Collections.list;

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
        for (var record : records) {
            add(record);
        }
    }

    public boolean same(RecordEntity... records) {
        return Collections.equalsIgnoreOrder(toList().stream().map(TableRecord::toEntity).toList(), list(records));
    }

    public abstract RecordCollection select(Function<TableRecord, Boolean> selectionCondition, RecordIterationLimiter limiter);

    public abstract void remove(RecordLambda<Boolean> selectionCondition, RecordIterationLimiter limiter);

    public abstract int count();

    public abstract Optional<TableRecord> find(TableRecordFinder finder);

    /**
     * Removes a record if finder finds one.
     *
     * @param finder the finder that is going to find a value to remove.
     * @since Quark 1.2
     */
    public abstract void exclude(TableRecordFinder finder);

    public abstract ArrayList<TableRecord> toList();

    @Override
    public String toString() {
        var buffer = new TextBuffer();

        for (var record : this) {
            buffer.appendLine(record);
        }

        return buffer.extractContent();
    }

    @NotNull
    @Override
    public Iterator<TableRecord> iterator() {
        return toList().iterator();
    }
}
