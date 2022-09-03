package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class IntegerType extends EntityType {
    public IntegerType() {
        super("int");
    }

    @Override
    public IntegerEntity makeEntity(String string) {
        return new IntegerEntity(Converter.toInteger(string));
    }

    @Override
    public String toInstructionForm(Entity integer) {
        return String.valueOf(integer.valueAs(Integer.class).intValue());
    }
}
