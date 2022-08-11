package ru.anafro.quark.server.databases.instructions.parser.states;

import ru.anafro.quark.server.databases.instructions.entities.InstructionEntity;

public class FloatEntity extends InstructionEntity {
    private final float value;

    public FloatEntity(float value) {
        super("float");
        this.value = value;
    }

    @Override
    public Float toObject() {
        return value;
    }
}
