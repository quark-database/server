package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ConstructorNameInstructionToken extends InstructionToken {
    public static final char CONSTRUCTOR_NAME_MARKER = '@';

    public ConstructorNameInstructionToken(String value) {
        super("constructor name", value);
    }

    @Override
    public String getPresentation() {
        return CONSTRUCTOR_NAME_MARKER + getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LOWER_ALPHA_WITH_SPACES);
    }
}
