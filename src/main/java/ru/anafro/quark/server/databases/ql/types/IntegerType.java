package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class IntegerType extends EntityType {
    public IntegerType() {
        super("int", "float", "long");
    }

    @Override
    public IntegerEntity makeEntity(String string) {
        return new IntegerEntity(Converter.toInteger(string));
    }

    @Override
    public String toInstructionForm(Entity integer) {
        return String.valueOf(integer.valueAs(Integer.class).intValue());
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch(entity.getTypeName()) {
            case "float" -> new IntegerEntity(entity.valueAs(Float.class).intValue());
            case "long" -> new IntegerEntity(entity.valueAs(Long.class).intValue());

            default -> null;
        };
    }
}
