package ru.anafro.quark.server.databases.ql.lexer.tokens;

public class EqualsInstructionToken extends InstructionToken {
    public EqualsInstructionToken() {
        super("equals sign", "=");
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
