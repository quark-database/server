package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.entities.exceptions.InstructionEntityCastException;

public abstract class InstructionEntity {
    public static final String WILDCARD_TYPE = "?";
    private final String type;

    public InstructionEntity(String type) {
        this.type = type;
    }

    @Deprecated
    public <T> T as(Class<T> clazz) {
        var object = this.getValue();

        if(clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new InstructionEntityCastException(object, clazz);
        }
    }

    public abstract Object getValue();

    public abstract String getValueAsString();

    public String getType() {
        return type;
    }
}
