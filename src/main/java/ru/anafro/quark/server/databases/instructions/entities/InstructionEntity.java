package ru.anafro.quark.server.databases.instructions.entities;

import ru.anafro.quark.server.databases.instructions.entities.exceptions.InstructionEntityCastException;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public abstract class InstructionEntity {
    private final String name;

    public InstructionEntity(String name) {
        this.name = name;
    }

    public <T> T as(Class<T> clazz) {
        var object = this.toObject();

        if(clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new InstructionEntityCastException(object, clazz);
        }
    }

    public abstract Object toObject();

    public String getName() {
        return name;
    }
}
