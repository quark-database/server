package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.IntegerEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class IntegerType extends EntityType<IntegerEntity> {
    public IntegerType() {
        super("int", int.class, IntegerEntity.class, "float", "long", "double", "str");
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
        return Entity.wrap(switch (entity.getTypeName()) {
            case "str" -> Converter.toInteger(entity.valueAs(String.class));
            case "float" -> entity.valueAs(Float.class).intValue();
            case "long" -> entity.valueAs(Long.class).intValue();
            case "double" -> entity.valueAs(Double.class).intValue();

            default -> null;
        });
    }
}
