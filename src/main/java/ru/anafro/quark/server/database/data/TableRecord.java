package ru.anafro.quark.server.database.data;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.files.TableHeader;
import ru.anafro.quark.server.database.views.TableViewRow;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.anafro.quark.server.utils.collections.Collections.emptyList;
import static ru.anafro.quark.server.utils.collections.Collections.list;
import static ru.anafro.quark.server.utils.objects.Nulls.nullByDefault;

public class TableRecord implements Iterable<RecordField> {
    private final List<RecordField> fields;

    public TableRecord(List<String> columnNames, List<Entity> fields) {
        this.fields = emptyList();

        Lists.forEachZipped(columnNames, fields, (columnName, field) -> {
            this.fields.add(new RecordField(columnName, field));
        });
    }

    public TableRecord(List<RecordField> fields) {
        this.fields = fields;
    }

    public TableRecord(TableHeader header, List<Entity> fields) {
        this(header.getColumns().stream().map(ColumnDescription::name).toList(), fields);
    }

    public static TableRecord record(RecordField... fields) {
        return new TableRecord(list(fields));
    }

    public static TableRecord record(TableHeader header, Object... fields) {
        return new TableRecord(header, Entity.wrapMany(fields));
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

    public List<RecordField> getFields() {
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

    public void add(String columnName, Entity value) {
        add(new RecordField(columnName, value));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableRecord that = (TableRecord) o;
        return Objects.equals(fields, that.fields);
    }

    public RecordEntity toEntity() {
        return new RecordEntity(fields.stream().map(RecordField::getEntity).toArray(Entity[]::new));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
