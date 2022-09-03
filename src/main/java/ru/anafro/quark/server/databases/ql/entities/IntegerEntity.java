package ru.anafro.quark.server.databases.ql.entities;

public class IntegerEntity extends Entity {
    private final int value;

    public IntegerEntity(int value) {
        super("int");
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return getValueAsString();
    }

    private String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public String toInstructionForm() {
        return getValueAsString();
    }
}
