package ru.anafro.quark.server.databases.ql.lexer;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;

public abstract class LiteralInstructionToken extends InstructionToken {
    public LiteralInstructionToken(String name, String value) {
        super(name, value);
    }

    public abstract InstructionEntity toEntity();
}
