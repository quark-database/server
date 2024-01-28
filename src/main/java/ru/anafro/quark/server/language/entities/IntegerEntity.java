package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.facade.Quark;

public class IntegerEntity extends Entity {
    private final int value;

    public IntegerEntity(int value) {
        super("int");
        this.value = value;
    }

    public IntegerEntity incremented() {
        return new IntegerEntity(value + 1);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String format() {
        return STR."<purple>\{value}</>";
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        return getValueAsString();
    }

    @Override
    public int rawCompare(Entity entity) {
        return value - ((IntegerEntity) entity).getValue();
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash(value);
    }

    private String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public String toInstructionForm() {
        return getValueAsString();
    }
}
