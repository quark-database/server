package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;
import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validators;

public class IntegerLiteralInstructionToken extends LiteralInstructionToken {
    public IntegerLiteralInstructionToken(String value) {
        super("integer", value);
    }

    @Override
    public String getRepresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.INTEGER_STRING);
    }

    @Override
    public IntegerEntity toEntity() {
        return new IntegerEntity(Converter.toInteger(getValue()));
    }
}
