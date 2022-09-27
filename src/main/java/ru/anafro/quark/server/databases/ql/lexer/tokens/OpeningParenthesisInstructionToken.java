package ru.anafro.quark.server.databases.ql.lexer.tokens;

public class OpeningParenthesisInstructionToken extends InstructionToken {
    public OpeningParenthesisInstructionToken() {
        super("opening parenthesis", "(");
    }

    @Override
    public String getRepresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return value.equals(getValue());
    }
}
