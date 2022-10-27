package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;

public class FloatEntity extends Entity {
    private final float value;

    public FloatEntity(float value) {
        super("float");
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    private String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public String toInstructionForm() {
        return getValueAsString();
    }

    @Override
    public String toRecordForm() {
        return String.valueOf(value);
    }

    @Override
    public int rawCompare(Entity entity) {
        return Float.compare(value, ((FloatEntity) entity).getValue());
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash((int) value);
    }
}
