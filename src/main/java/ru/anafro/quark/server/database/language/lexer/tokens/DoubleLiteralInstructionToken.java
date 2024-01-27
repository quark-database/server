package ru.anafro.quark.server.database.language.lexer.tokens;

import ru.anafro.quark.server.database.language.entities.DoubleEntity;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.utils.strings.Converter;

public class DoubleLiteralInstructionToken extends LiteralInstructionToken {
    public DoubleLiteralInstructionToken(String doubleValue) {
        super("long", doubleValue);
    }

    @Override
    public Entity toEntity() {
        return new DoubleEntity(Converter.toDouble(getValue()));
    }
}
