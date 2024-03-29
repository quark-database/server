package ru.anafro.quark.server.language.lexer.tokens;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.FloatEntity;
import ru.anafro.quark.server.utils.strings.Converter;

public class FloatLiteralInstructionToken extends LiteralInstructionToken {
    public FloatLiteralInstructionToken(String value) {
        super("float", value);
    }

    @Override
    public Entity toEntity() {
        return new FloatEntity(Converter.toFloat(getValue()));
    }
}
