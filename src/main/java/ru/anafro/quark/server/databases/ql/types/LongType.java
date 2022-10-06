package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class LongType extends EntityType {
    public LongType() {
        super("long", "str", "int", "float");
    }

    @Override
    public Entity makeEntity(String string) {
        var preparedString = string.replace("L", "");

        return new LongEntity(Converter.toLong(string));
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return entity.valueAs(Long.class).toString() + "L";
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch(entity.getTypeName()) {
            case "str" -> new LongEntity(Converter.toLong(entity.valueAs(String.class)));
            case "int" -> new LongEntity(entity.valueAs(Integer.class));
            case "float" -> new LongEntity(entity.valueAs(Float.class).longValue());

            default -> null;
        };
    }
}
