package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;

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
