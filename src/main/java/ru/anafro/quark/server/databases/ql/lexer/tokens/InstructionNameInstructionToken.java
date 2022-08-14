package ru.anafro.quark.server.databases.ql.lexer.tokens;

import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class InstructionNameInstructionToken extends InstructionToken {
    public InstructionNameInstructionToken(String value) {
        super("instruction name", value);
    }

    @Override
    public String getPresentation() {
        return getValue();
    }

    @Override
    public boolean isValueValid(String value) {
        return Validators.validate(value, Validators.LOWER_ALPHA_WITH_SPACES);
    }
}
