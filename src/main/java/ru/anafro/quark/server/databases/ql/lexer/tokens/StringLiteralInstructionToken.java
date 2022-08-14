package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.LiteralInstructionToken;

public class StringLiteralInstructionToken extends LiteralInstructionToken {
    public static final char STRING_LITERAL_QUOTE = '"';

    public StringLiteralInstructionToken(String value) {
        super("string literal", value);
    }

    @Override
    public String getPresentation() {
        return STRING_LITERAL_QUOTE + getValue() + STRING_LITERAL_QUOTE;
    }

    @Override
    public boolean isValueValid(String value) {
        return true; // Strings can contain anything
    }


    @Override
    public InstructionEntity toEntity() {
        return new StringEntity(getValue());
    }
}
