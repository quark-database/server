package ru.anafro.quark.server.databases.ql.entities;

public class BooleanEntity extends InstructionEntity {
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
    public String getValueAsString() {
        return String.valueOf(value);
    }
}
