package ru.anafro.quark.server.databases.ql.entities;

public class IntegerEntity extends InstructionEntity {
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
    public String getValueAsString() {
        return String.valueOf(value);
    }
}
