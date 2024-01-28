package ru.anafro.quark.server.language.lexer.tokens;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.LongEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class LongLiteralInstructionToken extends LiteralInstructionToken {
    public LongLiteralInstructionToken(String longValue) {
        super("long", longValue);
    }

    @Override
    public Entity toEntity() {
        return new LongEntity(Converter.toLong(getValue()));
    }
}
