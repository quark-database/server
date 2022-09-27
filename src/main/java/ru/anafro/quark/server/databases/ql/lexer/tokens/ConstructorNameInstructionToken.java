package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;
import ru.anafro.quark.server.databases.ql.exceptions.NoSuchEntityConstructorException;
import ru.anafro.quark.server.utils.validation.Validators;

public class ConstructorNameInstructionToken extends InstructionToken {
    public static final char CONSTRUCTOR_NAME_MARKER = '@';

    public ConstructorNameInstructionToken(String value) {
        super("constructor name", value);
    }

    @Override
    public String getRepresentation() {
        return CONSTRUCTOR_NAME_MARKER + getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LOWER_ALPHA_WITH_SPACES);
    }

    public EntityConstructor getConstructor() {
        if(!Quark.constructors().has(getValue())) {
            throw new NoSuchEntityConstructorException(getValue());
        }

        return Quark.constructors().get(getValue());
    }
}
