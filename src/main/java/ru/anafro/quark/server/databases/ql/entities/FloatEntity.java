package ru.anafro.quark.server.databases.ql.entities;

public class FloatEntity extends InstructionEntity {
    private final float value;

    public FloatEntity(float value) {
        super("float");
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }
}
