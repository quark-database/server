package ru.anafro.quark.server.databases.ql.entities;

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
}
