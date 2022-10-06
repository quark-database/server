package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class StringType extends EntityType {
    public StringType() {
        super("str", "int", "float", "boolean", "long");
    }

    @Override
    public StringEntity makeEntity(String string) {
        return new StringEntity(string);
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return quoted(((StringEntity) entity).getValue());
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch(entity.getTypeName()) {
            case "int" -> new StringEntity(entity.valueAs(Integer.class).toString());
            case "float" -> new StringEntity(entity.valueAs(Float.class).toString());
            case "boolean" -> new StringEntity(entity.valueAs(Boolean.class).toString());
            case "long" -> new StringEntity(entity.valueAs(Long.class).toString());
            default -> null;
        };
    }
}
