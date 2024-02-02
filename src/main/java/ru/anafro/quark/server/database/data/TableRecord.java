package ru.anafro.quark.server.database.data;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.exceptions.RecordFieldCountMismatchesTableHeaderException;
import ru.anafro.quark.server.database.data.exceptions.RecordTypeMismatchesTableHeaderException;
import ru.anafro.quark.server.database.views.TableViewRow;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.ListEntity;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.anafro.quark.server.utils.objects.Nulls.nullByDefault;

public class TableRecord implements Iterable<RecordField> {
    private final Table table;
    private final ArrayList<RecordField> fields;

    public TableRecord(Table table, ArrayList<Entity> fields) {
        this.table = table;
        this.fields = Lists.empty();

        var columns = table.columns();

        if (fields.size() != columns.size() - columns.stream().filter(ColumnDescription::isGenerated).count()) {
            throw new RecordFieldCountMismatchesTableHeaderException(table, fields.size());
        }

        var fieldIndex = new AtomicInteger();
        for (var column : columns) {
            column.tryGetGeneratingModifier().ifPresentOrElse(entity -> {
                var field = new RecordField(column.name(), null);
                var modifier = entity.getModifier();
                var arguments = entity.getModifierArguments();

                modifier.prepareField(table, field, arguments);

                this.fields.add(field);
            }, () -> {
                var value = fields.get(fieldIndex.getAndIncrement());
                var columnType = column.type();

                if (columnType.canBeCastedFrom(value.getType())) {
                    value = columnType.cast(value);
                }

                if (value.doesntHaveType(columnType)) {
                    throw new RecordTypeMismatchesTableHeaderException(table, column, value);
                }

                this.fields.add(new RecordField(column.name(), value));
            });
        }
    }

    public TableRecord(Table table, Object... fields) {
        this(table, ListEntity.of(fields).getValue());
    }

    public RecordField getField(String name) {
        for (var field : fields) {
            if (field.getColumnName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    private <T> T getFieldAs(String fieldName, Class<T> type) {
        var field = getField(fieldName);

        return nullByDefault(field, f -> f.getEntity().valueAs(type));
    }

    public int getInt(String fieldName) {
        return getFieldAs(fieldName, Integer.class);
    }

    public long getLong(String fieldName) {
        return getFieldAs(fieldName, Long.class);
    }

    public String getString(String fieldName) {
        return getFieldAs(fieldName, String.class);
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

    public void add(RecordField field) {
        fields.add(field);
    }

    @NotNull
    @Override
    public Iterator<RecordField> iterator() {
        return fields.iterator();
    }

    public boolean doesntHaveField(String name) {
        return !hasField(name);
    }

    public int fieldCount() {
        return fields.size();
    }

    public String toTableLine() {
        return fields.stream().map(field -> field.getEntity().toRecordForm()).collect(Collectors.joining(","));
    }

    public RecordField fieldAt(int index) {
        return fields.get(index);
    }

    public TableViewRow toTableViewRow() {
        return new TableViewRow(fields.toArray());
    }

    public void removeField(String columnName) {
        fields.removeIf(field -> field.getColumnName().equals(columnName));
    }

    public void reorderFields(List<String> newOrder) {
        var reorderedFields = new ArrayList<RecordField>();

        for (var nextFieldName : newOrder) {
            reorderedFields.add(fields.stream().filter(field -> field.getColumnName().equals(nextFieldName)).findFirst().orElseThrow());
        }

        fields.clear();
        fields.addAll(reorderedFields);
    }

    @Override
    public String toString() {
        return Lists.join(fields, field -> field.getEntity().toInstructionForm());
    }
}
