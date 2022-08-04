package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;

public class StringLiteralInstructionToken extends InstructionToken {
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
}
