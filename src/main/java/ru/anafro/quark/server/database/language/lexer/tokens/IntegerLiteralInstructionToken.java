package ru.anafro.quark.server.database.language.lexer.tokens;

import ru.anafro.quark.server.database.language.entities.IntegerEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class IntegerLiteralInstructionToken extends LiteralInstructionToken {
    public IntegerLiteralInstructionToken(String value) {
        super("integer", value);
    }

    @Override
    public IntegerEntity toEntity() {
        return new IntegerEntity(Converter.toInteger(getValue()));
    }
}
