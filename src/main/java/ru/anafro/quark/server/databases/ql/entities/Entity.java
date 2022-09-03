package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.exceptions.InstructionEntityCastException;
import ru.anafro.quark.server.databases.ql.types.EntityType;

public abstract class Entity {
    public static final String WILDCARD_TYPE = "?";
    private final EntityType type;

    public Entity(String type) {
        this.type = Quark.types().get(type);
    }

    @Deprecated
    public <T> T valueAs(Class<T> clazz) {
        var object = this.getValue();

        if(clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new InstructionEntityCastException(object, clazz);
        }
    }

    public String toInstructionForm() {
        return type.toInstructionForm(this);
    }

    public EntityType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public boolean hasType(EntityType type) {
        return type.equals(this.type);
    }

    public boolean hasType(String typeName) {
        return hasType(Quark.types().get(typeName));
    }

    public boolean mismatchesType(EntityType type) {
        return !hasType(type);
    }

    public boolean mismatchesType(String typeName) {
        return mismatchesType(Quark.types().get(typeName));
    }

    public abstract Object getValue();

    public abstract String getExactTypeName();

    public abstract String toRecordForm();
}
