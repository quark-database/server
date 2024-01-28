package ru.anafro.quark.server.language.lexer.tokens;

import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.exceptions.NoSuchEntityConstructorException;
import ru.anafro.quark.server.facade.Quark;

public class ConstructorNameInstructionToken extends InstructionToken {
    public static final char CONSTRUCTOR_PREFIX = '@';

    public ConstructorNameInstructionToken(String value) {
        super("constructor name", value);
    }

    public EntityConstructor getConstructor() {
        if (!Quark.constructors().has(getValue())) {
            throw new NoSuchEntityConstructorException(getValue());
        }

        return Quark.constructors().get(getValue());
    }
}
