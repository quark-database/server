package ru.anafro.quark.server.language.lexer.tokens;

import ru.anafro.quark.server.language.entities.Entity;

public abstract class LiteralInstructionToken extends InstructionToken {
    public LiteralInstructionToken(String name, String value) {
        super(name, value);
    }

    public abstract Entity toEntity();
}
