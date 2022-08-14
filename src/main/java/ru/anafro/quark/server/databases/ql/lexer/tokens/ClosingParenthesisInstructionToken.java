package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;

public class ClosingParenthesisInstructionToken extends InstructionToken {

    public ClosingParenthesisInstructionToken() {
        super("closing parenthesis", ")");
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
