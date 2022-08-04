package ru.anafro.quark.server.utils.validation.validators;

import ru.anafro.quark.server.utils.validation.Validator;

public class IsIntegerValidator implements Validator<String> {
    @Override
    public boolean isValid(String value) {
        try {
            int ignored = Integer.parseInt(value);
            return true;
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
