package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.NullEntity;

public class RecordField {
    private final String columnName;
    private Entity value;

    public RecordField(String columnName, Entity value) {
        this.columnName = columnName;
        this.value = value;
    }

    public static RecordField field(String columnName, Object value) {
        return new RecordField(columnName, Entity.wrap(value));
    }

    public static RecordField empty(String columnName) {
        return new RecordField(columnName, new NullEntity());
    }

    public String getColumnName() {
        return columnName;
    }

    public Entity getEntity() {
        return value;
    }

    public void set(Entity value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null || value.hasType("null");
    }
}
