package ru.anafro.quark.server.databases.instructions.parser.states;

import ru.anafro.quark.server.databases.instructions.entities.InstructionEntity;

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
