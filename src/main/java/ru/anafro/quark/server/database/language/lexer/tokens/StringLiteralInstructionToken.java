package ru.anafro.quark.server.database.language.lexer.tokens;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.StringEntity;

public class StringLiteralInstructionToken extends LiteralInstructionToken {
    public static final char STRING_LITERAL_QUOTE = '"';

    public StringLiteralInstructionToken(String value) {
        super("string literal", value);
    }


    @Override
    public Entity toEntity() {
        return new StringEntity(getValue());
    }
}
