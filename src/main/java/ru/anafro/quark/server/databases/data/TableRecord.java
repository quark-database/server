package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.RecordFieldCountMismatchesTableHeaderException;
import ru.anafro.quark.server.databases.data.exceptions.RecordTypeMismatchesTableHeaderException;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.views.TableViewRow;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.integers.Counter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TableRecord implements Iterable<RecordField> {
    private final Table table;
    private final ArrayList<RecordField> fields;

    public TableRecord(Table table, ListEntity entities) {
        this.table = table;
        this.fields = Lists.empty();

        // TODO: replace with a table.requireValidity();
        if(table.getHeader().getColumns().size() != entities.size() + table.getHeader().getModifiers().stream().map(ColumnModifierEntity::getModifier).filter(ColumnModifier::areValuesShouldBeGenerated).count()) {
            throw new RecordFieldCountMismatchesTableHeaderException(table, entities.size());
        }
        // TODO: --------------------------------------

        var columns = table.getHeader().getColumns();
        var fieldIndex = new Counter();

        for(var columnIndex = new Counter(); columnIndex.getCount() < columns.size(); columnIndex.count()) {
            var column = columns.get(columnIndex.getCount());

            if(table.getHeader().getModifiers().stream().anyMatch(e -> e.getModifier().areValuesShouldBeGenerated() && e.getColumnName().equals(column.getName()))) {
                var generatingModifier = table.getHeader().getModifiers().stream().filter(modifierEntity -> modifierEntity.getModifier().areValuesShouldBeGenerated() && modifierEntity.getColumnName().equals(column.getName())).findFirst().get();
                var field = new RecordField(columns.get(columnIndex.getCount()).getName(), null);

                generatingModifier.getModifier().beforeRecordInsertion(table, field, generatingModifier.getModifierArguments());

                fields.add(field);
            } else {
                var value = entities.valueAt(fieldIndex.getCount());

                if(column.getType().castableFrom(value.getType())) {
                    value = column.getType().cast(value);
                }

                if(value.mismatchesType(column.getType())) {
                    throw new RecordTypeMismatchesTableHeaderException(table, column, value);
                }

                fields.add(new RecordField(column.getName(), value));

                fieldIndex.count();
            }
        }
    }

    public TableRecord(Table table, ArrayList<RecordField> fields) {
        this.table = table;
        this.fields = fields;
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

    public void addField(RecordField field) {
        fields.add(field);
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
        return new TableViewRow(fields.stream().map(recordField -> recordField.getValue().getValue().toString()).toArray(String[]::new));
    }

    public void removeField(String columnName) {
        fields.removeIf(field -> field.getColumnName().equals(columnName));
    }

    public void reorderFields(List<String> newOrder) {
        var reorderedFields = new ArrayList<RecordField>();

        for(var nextFieldName : newOrder) {
            reorderedFields.add(fields.stream().filter(field -> field.getColumnName().equals(nextFieldName)).findFirst().get());
        }

        fields.clear();
        fields.addAll(reorderedFields);
    }

    @Override
    public String toString() {
        return Lists.joinPresentations(fields, field -> field.getValue().toInstructionForm());
    }
}
