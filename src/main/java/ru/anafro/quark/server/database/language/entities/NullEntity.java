package ru.anafro.quark.server.database.language.entities;

import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.facade.Quark;

public class NullEntity extends Entity {
    private final String exactNullType;

    public NullEntity(String exactNullType) {
        super("null");
        this.exactNullType = exactNullType;
    }

    public NullEntity() {
        this("any");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String format() {
        return STR."<gray>\{new StringConstructorBuilder().name("null").argument(new StringEntity(exactNullType)).build()}</>";
    }

    @Override
    public String getExactTypeName() {
        return STR."\{this.getTypeName()} of \{exactNullType}";
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
