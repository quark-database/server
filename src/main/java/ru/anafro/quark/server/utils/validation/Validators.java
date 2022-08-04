package ru.anafro.quark.server.utils.validation;

import ru.anafro.quark.server.utils.validation.validators.IsFloatValidator;
import ru.anafro.quark.server.utils.validation.validators.IsIntegerValidator;

public interface Validators {
    Validator<String> LOWER_ALPHA = new RegexValidator("^[a-z]*$");
    Validator<String> ALPHA = new RegexValidator("^[a-zA-Z]*$");
    Validator<String> ALPHANUMERIC = new RegexValidator("^[a-zA-Z0-9]*$");
    Validator<String> UPPER_ALPHA = new RegexValidator("^[A-Z]*$");
    Validator<String> UPPER_ALPHANUMERIC = new RegexValidator("^[A-Z0-9]*$");
    Validator<String> LOWER_ALPHA_WITH_SPACES = new RegexValidator("^[a-z ]*$");
    Validator<String> LOWER_ALPHANUMERIC_WITH_SPACES = new RegexValidator("^[A-Z0-9 ]*$");
    Validator<String> UPPER_ALPHA_WITH_SPACES = new RegexValidator("^[A-Z ]*$");
    Validator<String> UPPER_ALPHANUMERIC_WITH_SPACES = new RegexValidator("^[A-Z0-9 ]*$");
    Validator<String> ALPHA_WITH_SPACES = new RegexValidator("^[a-zA-Z ]*$");
    Validator<String> ALPHANUMERIC_WITH_SPACES = new RegexValidator("^[a-zA-Z0-9 ]*$");
    Validator<String> FLOAT_STRING = new IsFloatValidator();
    Validator<String> INTEGER_STRING = new IsIntegerValidator();
    Validator<Character> IS_LATIN = character -> (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');

    static <T> boolean validate(T value, Validator<T> validator) {
        return validator.isValid(value);
    }
}
