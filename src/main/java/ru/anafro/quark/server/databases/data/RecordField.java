package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public class RecordField {
    private final String columnName;
    private Entity value;

    public RecordField(String columnName, Entity value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Entity getValue() {
        return value;
    }

    public void setValue(Entity value) {
        this.value = value;
    }
}
