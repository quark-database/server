package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.StringEntity;

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
        return entity.toInstructionForm();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return Entity.wrap(entity.toString());
    }
}
