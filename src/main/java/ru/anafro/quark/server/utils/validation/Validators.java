package ru.anafro.quark.server.utils.validation;

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
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Validators#validate(Object, Validator)
 * @since Quark 1.1
 */
public interface Validators {
    Validator<String> FLOAT_STRING = new IsFloatValidator();
    Validator<String> INTEGER_STRING = new IsIntegerValidator();
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
     * <p>
     * It is recommended to use this method for default validators
     * stored inside the {@code Validators} class.
     *
     * @param value     the validating value
     * @param validator the validator
     * @param <T>       the validation object type
     * @return the validation result
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Validator
     * @since Quark 1.1
     */
    static <T> boolean validate(T value, Validator<T> validator) {
        return validator.isValid(value);
    }
}
