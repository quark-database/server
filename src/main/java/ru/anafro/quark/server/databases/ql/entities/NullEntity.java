package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;

public class NullEntity extends Entity {
    private final String exactNullType;

    public NullEntity(String exactNullType) {
        super("null");
        this.exactNullType = exactNullType;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getExactTypeName() {
        return this.getTypeName() + " of " + exactNullType;
    }

    @Override
    public String toRecordForm() {
        return "@";
    }

    @Override
    public int rawCompare(Entity entity) {
        return 0;
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash("null");
    }
}
