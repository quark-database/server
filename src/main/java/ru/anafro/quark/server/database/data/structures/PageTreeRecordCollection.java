package ru.anafro.quark.server.database.data.structures;

import ru.anafro.quark.server.database.data.RecordIterationLimiter;
import ru.anafro.quark.server.database.data.RecordLambda;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.utils.collections.BTree;
import ru.anafro.quark.server.utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Optional;

public class PageTreeRecordCollection extends RecordCollection {
    private final String keyColumn;
    private final BTree<Entity, TableRecord> tree = new BTree<>();

    public PageTreeRecordCollection(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    @Override
    public void add(TableRecord record) {
        tree.put(record.getField(keyColumn).getEntity(), record);
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
        return tree.size();
    }

    @Override
    public Optional<TableRecord> find(TableRecordFinder finder) {
        var found = tree.get(finder.findingValue());

        if (found == null) {
            return Optional.empty();
        } else {
            return Optional.of(found);
        }
    }

    /**
     * Removes a record if finder finds one.
     *
     * @param finder the finder that is going to find a value to remove.
     * @since Quark 1.2
     */
    @Override
    public void exclude(TableRecordFinder finder) {
        tree.remove(finder.findingValue());
    }

    @Override
    public ArrayList<TableRecord> toList() {
        return tree.toList();
    }

    public BTree<Entity, TableRecord> getTree() {
        return tree;
    }

    public String getKeyColumn() {
        return keyColumn;
    }
}
