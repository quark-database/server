package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;

public class ColonInstructionToken extends InstructionToken {

    public ColonInstructionToken() {
        super("colon", ":");
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
