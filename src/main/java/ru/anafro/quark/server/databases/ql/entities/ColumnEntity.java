package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.ColumnDescription;

public class ColumnEntity extends Entity {
    private final ColumnDescription columnDescription;

    public ColumnEntity(ColumnDescription columnDescription) {
        super("column");
        this.columnDescription = columnDescription;
    }

    @Override
    public ColumnDescription getValue() {
        return columnDescription;
    }

    @Override
    public String getValueAsString() {
        return "<column %s with name %s>".formatted(columnDescription.getType(), columnDescription.getName());
    }
}
