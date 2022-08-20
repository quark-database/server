package ru.anafro.quark.server.utils.validation.validators;

import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validator;
import ru.anafro.quark.server.utils.validation.Validators;

/**
 * A string validator that checks whether string represents an integer or not.
 * Please, use the {@code Validators.INTEGER_STRING} instead of creating a new instance.
 *
 * <pre>
 * {@code
 * Validators.INTEGER_STRING.isValid("42");     // true
 * Validators.INTEGER_STRING.isValid("anafro"); // false
 * }
 * </pre>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Validators#INTEGER_STRING
 * @see    Validator
 */
public class IsIntegerValidator implements Validator<String> {

    /**
     * Validates the passed string. If string can be parsed to an integer,
     * <code>true</code> will be returned. Otherwise, if the string cannot
     * be parsed to an integer, <code>false</code> will be returned.
     *
     * @param  value an object to validate
     * @return a boolean represents the validation result (see the description above).
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Validators#INTEGER_STRING
     * @see    Validator
     * @see    Validators
     * @see    Converter#toInteger(String)
     */
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
