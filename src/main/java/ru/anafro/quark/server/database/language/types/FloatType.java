package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.FloatEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class FloatType extends EntityType<FloatEntity> {
    public FloatType() {
        super("float", float.class, FloatEntity.class, "int", "long", "double");
    }

    @Override
    public FloatEntity makeEntity(String string) {
        return new FloatEntity(Converter.toFloat(string));
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return String.valueOf(entity.valueAs(Float.class));
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch (entity.getTypeName()) {
            case "int" -> new FloatEntity(entity.valueAs(Integer.class));
            case "long" -> new FloatEntity(entity.valueAs(Long.class));
            case "double" -> new FloatEntity(entity.valueAs(Double.class).floatValue());

            default -> null;
        };
    }
}
