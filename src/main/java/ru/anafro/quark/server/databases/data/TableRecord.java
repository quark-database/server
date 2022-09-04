package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.RecordFieldCountMismatchesTableHeaderException;
import ru.anafro.quark.server.databases.data.exceptions.RecordTypeMismatchesTableHeaderException;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class TableRecord implements Iterable<RecordField> {
    private final Table table;
    private final ArrayList<RecordField> fields = Lists.empty();

    public TableRecord(Table table, ListEntity entities) {
        this.table = table;

        // TODO: replace with a table.requireValidity();
        if(table.getHeader().getColumns().size() != entities.size()) {
            throw new RecordFieldCountMismatchesTableHeaderException(table, entities.size());
        }
        // TODO: --------------------------------------

        var columns = table.getHeader().getColumns();

        for(int index = 0; index < columns.size(); index++) {
            var column = columns.get(index);
            var value = entities.valueAt(index);

            if(!value.hasType(column.getType())) {
                throw new RecordTypeMismatchesTableHeaderException(table, column, value);
            }
        }
    }

    public RecordField getField(String name) {
        for(var field : fields) {
            if(field.getColumnName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    public boolean hasField(String name) {
        return getField(name) != null;
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<RecordField> getFields() {
        return fields;
    }

    @Override
    public Iterator<RecordField> iterator() {
        return fields.iterator();
    }

    public boolean missingField(String name) {
        return !hasField(name);
    }

    public int fieldCount() {
        return fields.size();
    }

    public String toTableLine() {
        return fields.stream().map(field -> field.getValue().toRecordForm()).collect(Collectors.joining(","));
    }

    public RecordField fieldAt(int index) {
        return fields.get(index);
    }

    public TableViewRow toTableViewRow() {
        return new TableViewRow(fields.stream().map(recordField -> recordField.getValue().toRecordForm()).toArray(String[]::new));
    }
}
