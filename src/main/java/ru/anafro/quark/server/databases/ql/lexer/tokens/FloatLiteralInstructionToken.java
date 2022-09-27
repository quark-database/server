package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

public class FloatLiteralInstructionToken extends LiteralInstructionToken {
    public FloatLiteralInstructionToken(String value) {
        super("float", value);
    }

    @Override
    public String getRepresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.FLOAT_STRING);
    }

    @Override
    public Entity toEntity() {
        return new FloatEntity(Converter.toFloat(getValue()));
    }
}
