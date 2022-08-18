package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntity;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;

public abstract class LiteralInstructionToken extends InstructionToken {
    public LiteralInstructionToken(String name, String value) {
        super(name, value);
    }

    public abstract InstructionEntity toEntity();
}
