package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class IntegerLiteralInstructionToken extends InstructionToken {
    public IntegerLiteralInstructionToken(String value) {
        super("integer", value);
    }

    @Override
    public String getPresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.INTEGER_STRING);
    }
}
