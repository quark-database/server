package ru.anafro.quark.server.database.language.entities;

import ru.anafro.quark.server.facade.Quark;

public class DoubleEntity extends Entity {
    private final double value;

    public DoubleEntity(double value) {
        super("double");
        this.value = value;
    }

    public double getDouble() {
        return value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String format() {
        return STR."<purple>\{value}</><blue>D</>";
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
        return Double.compare(value, ((DoubleEntity) entity).getDouble());
    }

    @Override
    public int hashCode() {
        return Quark.integerHashingFunction().hash((int) value);
    }
}
