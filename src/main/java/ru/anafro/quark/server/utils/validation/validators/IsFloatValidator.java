package ru.anafro.quark.server.utils.validation.validators;

import ru.anafro.quark.server.utils.validation.Validator;

public class IsFloatValidator implements Validator<String> {
    @Override
    public boolean isValid(String value) {
        try {
            float ignored = Float.parseFloat(value);
            return true;
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
