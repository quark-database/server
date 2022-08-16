package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.InstructionEntityCastException;

public abstract class InstructionEntity {
    private final String name;

    public InstructionEntity(String name) {
        this.name = name;
    }

    public <T> T as(Class<T> clazz) {
        var object = this.getValue();

        if(clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new InstructionEntityCastException(object, clazz);
        }
    }

    public abstract Object getValue();

    public String getName() {
        return name;
    }
}
