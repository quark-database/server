package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;

public class LongEntity extends Entity {
    private final long value;

    public LongEntity(long value) {
        super("long");
        this.value = value;
    }

    @Override
    public Long getValue() {
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
        return Long.compare(value, ((LongEntity) entity).getValue());
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash((int) value);
    }

    public long getLong() {
        return value;
    }
}
