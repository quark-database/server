package ru.anafro.quark.server.database.language.lexer.tokens;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.LongEntity;
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
