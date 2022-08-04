package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;

public class EqualsInstructionToken extends InstructionToken {
    public EqualsInstructionToken() {
        super("equals", "=");
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
