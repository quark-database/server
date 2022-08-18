package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.utils.validation.Validators;

public class ArgumentNameInstructionToken extends InstructionToken {
    public static final String ARGUMENT_NAME_MARKER = "%";

    public ArgumentNameInstructionToken(String value) {
        super("argument name", value);
    }

    @Override
    public String getPresentation() {
        return ARGUMENT_NAME_MARKER + getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LOWER_ALPHA);
    }
}
