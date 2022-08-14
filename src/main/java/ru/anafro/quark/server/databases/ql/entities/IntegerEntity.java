package ru.anafro.quark.server.databases.ql.entities;

public class IntegerEntity extends InstructionEntity {
    private final int value;

    public IntegerEntity(int value) {
        super("int");
        this.value = value;
    }

    @Override
    public Integer toObject() {
        return value;
    }
}
