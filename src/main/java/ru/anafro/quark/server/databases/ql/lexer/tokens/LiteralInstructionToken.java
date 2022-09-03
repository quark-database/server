package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public abstract class LiteralInstructionToken extends InstructionToken {
    public LiteralInstructionToken(String name, String value) {
        super(name, value);
    }

    public abstract Entity toEntity();
}
