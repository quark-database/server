package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class StringType extends EntityType {
    public StringType() {
        super("str");
    }

    @Override
    public StringEntity makeEntity(String string) {
        return new StringEntity(string);
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return quoted(((StringEntity) entity).getValue());
    }
}
