package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;

public class BooleanEntity extends Entity {
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
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return String.valueOf(value);
    }

    @Override
    public int rawCompare(Entity entity) {
        return Boolean.compare(value, ((BooleanEntity) entity).getValue());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(Boolean.toString(value));
    }
}
