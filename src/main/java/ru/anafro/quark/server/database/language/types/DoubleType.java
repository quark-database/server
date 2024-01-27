package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.entities.DoubleEntity;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.utils.strings.Converter;

public class DoubleType extends EntityType<DoubleEntity> {
    public DoubleType() {
        super("double", double.class, DoubleEntity.class, "str", "float", "int", "long");
    }

    @Override
    public DoubleEntity makeEntity(String string) {
        return new DoubleEntity(Converter.toDouble(string.replace("D", "")));
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return STR."\{entity.valueAs(Double.class).toString()}D";
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch (entity.getTypeName()) {
            case "str" -> new DoubleEntity(Converter.toDouble(entity.valueAs(String.class)));
            case "int" -> new DoubleEntity(entity.valueAs(Integer.class));
            case "float" -> new DoubleEntity(entity.valueAs(Float.class).doubleValue());
            case "long" -> new DoubleEntity(entity.valueAs(Long.class).doubleValue());

            default -> null;
        };
    }
}
