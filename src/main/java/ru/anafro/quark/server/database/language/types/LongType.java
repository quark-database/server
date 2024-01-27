package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.LongEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class LongType extends EntityType<LongEntity> {
    public LongType() {
        super("long", long.class, LongEntity.class, "str", "int", "float", "double");
    }

    @Override
    public LongEntity makeEntity(String string) {
        var preparedString = string.replace("L", "");

        return new LongEntity(Converter.toLong(preparedString));
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return STR."\{entity.valueAs(Long.class).toString()}L";
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch (entity.getTypeName()) {
            case "str" -> new LongEntity(Converter.toLong(entity.valueAs(String.class)));
            case "int" -> new LongEntity(entity.valueAs(Integer.class));
            case "float" -> new LongEntity(entity.valueAs(Float.class).longValue());
            case "double" -> new LongEntity(entity.valueAs(Double.class).longValue());

            default -> null;
        };
    }
}
