package ru.anafro.quark.server.utils.validation;

import ru.anafro.quark.server.utils.validation.validators.InLongValidator;
import ru.anafro.quark.server.utils.validation.validators.IsFloatValidator;
import ru.anafro.quark.server.utils.validation.validators.IsIntegerValidator;

// TODO:
//  Probably there's a better syntax can be built for validation,
//  because "Validators.XXX.isValid()" looks a bit verbose.

/**
 * Validators class contains default value validators for
 * simple types, such as strings, characters and numbers.
 * Use validator constants inside this class.
 *
 * <pre>
 * {@code
 * Validators.LOWER_ALPHA.isValid("anafro");    // true
 * Validators.LOWER_ALPHA.isValid("Anafro");    // false
 * }
 * </pre>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Validators#validate(Object, Validator)
 * @see    Validators#ALPHA
 * @see    Validators#ALPHANUMERIC
 * @see    Validators#INTEGER_STRING
 */
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
    Validator<String> LONG_VALUE = new InLongValidator();
    Validator<Character> IS_LATIN = character -> (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');

    /**
     * Provides a better syntax for validation system. Pass the validation object
     * and the validator to this method to validate the object.
     *
     * <pre>
     * {@code
     * Validator<String> isQuarkDeveloper = developer -> developer.equals("anafro");
     *
     * // this:
     * Validators.validate("anafro", isQuarkDeveloper);
     *
     * // is an equivalent to:
     * isQuarkDeveloper.isValid("anafro");
     * }
     * </pre>
     *
     * It is recommended to use this method for default validators
     * stored inside the {@code Validators} class.
     *
     * @param value     the validating value
     * @param validator the validator
     * @return          the validation result
     * @param <T>       the validation object type
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Validator
     */
    static <T> boolean validate(T value, Validator<T> validator) {
        return validator.isValid(value);
    }
}
