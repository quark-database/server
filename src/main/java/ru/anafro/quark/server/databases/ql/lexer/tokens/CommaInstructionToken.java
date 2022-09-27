package ru.anafro.quark.server.databases.ql.lexer.tokens;

public class CommaInstructionToken extends InstructionToken {
    public CommaInstructionToken() {
        super("comma", ",");
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
