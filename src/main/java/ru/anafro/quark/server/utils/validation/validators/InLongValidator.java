package ru.anafro.quark.server.utils.validation.validators;

import ru.anafro.quark.server.utils.validation.Validator;

public class InLongValidator implements Validator<String> {
    @Override
    public boolean isValid(String value) {
        try {
            long ignored = Long.parseLong(value);
            return true;
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
