package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;

public class SemicolonInstructionToken extends InstructionToken {
    public SemicolonInstructionToken() {
        super("semicolon", ";");
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
