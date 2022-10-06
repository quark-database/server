package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

public class LongLiteralInstructionToken extends LiteralInstructionToken {
    public LongLiteralInstructionToken(String longValue) {
        super("long", longValue);
    }

    @Override
    public String getRepresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LONG_VALUE);
    }

    @Override
    public Entity toEntity() {
        return new LongEntity(Converter.toLong(getValue()));
    }
}
