package ru.anafro.quark.server.databases.ql.lexer.tokens;

public class ColonInstructionToken extends InstructionToken {

    public ColonInstructionToken() {
        super("colon", ":");
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
