package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;

public class OpeningParenthesisInstructionToken extends InstructionToken {
    public OpeningParenthesisInstructionToken() {
        super("opening parenthesis", "(");
    }

    @Override
    public String getPresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return value.equals(getValue());
    }
}
