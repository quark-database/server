package ru.anafro.quark.server.databases.ql.lexer.tokens;

public class SemicolonInstructionToken extends InstructionToken {
    public SemicolonInstructionToken() {
        super("semicolon", ";");
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
