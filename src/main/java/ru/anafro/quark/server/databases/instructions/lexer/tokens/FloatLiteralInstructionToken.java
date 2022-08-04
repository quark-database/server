package ru.anafro.quark.server.databases.instructions.lexer.tokens;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class FloatLiteralInstructionToken extends InstructionToken {
    public FloatLiteralInstructionToken(String value) {
        super("float", value);
    }

    @Override
    public String getPresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.FLOAT_STRING);
    }
}
