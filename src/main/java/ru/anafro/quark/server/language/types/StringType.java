package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.StringEntity;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class StringType extends EntityType<StringEntity> {
    public StringType() {
        super("str", String.class, StringEntity.class, "float", "boolean", "long", "double", "int", "date");
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
        return Entity.wrap(entity.toString());
    }
}
