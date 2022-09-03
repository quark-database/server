package ru.anafro.quark.server.databases.ql.entities;

public class BooleanEntity extends Entity {
    private final boolean value;

    public BooleanEntity(boolean value) {
        super("boolean");
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return String.valueOf(value);
    }
}
