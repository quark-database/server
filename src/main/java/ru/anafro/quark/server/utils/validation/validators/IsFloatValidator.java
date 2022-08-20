package ru.anafro.quark.server.utils.validation.validators;

import ru.anafro.quark.server.utils.strings.Converter;
import ru.anafro.quark.server.utils.validation.Validator;
import ru.anafro.quark.server.utils.validation.Validators;

/**
 * A string validator that checks whether string represents a float or not.
 * Please, use the {@code Validators.FLOAT_STRING} instead of creating a new instance.
 *
 * <pre>
 * {@code
 * Validators.FLOAT_STRING.isValid("3.14");     // true
 * Validators.FLOAT_STRING.isValid("anafro");   // false
 * }
 * </pre>
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Validators#INTEGER_STRING
 * @see    Validator
 */
public class IsFloatValidator implements Validator<String> {

    /**
     * Validates the passed string. If string can be parsed to a float,
     * <code>true</code> will be returned. Otherwise, if the string cannot
     * be parsed to a float, <code>false</code> will be returned.
     *
     * @param  value an object to validate
     * @return a boolean represents the validation result (see the description above).
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Validators#FLOAT_STRING
     * @see    Validator
     * @see    Validators
     * @see    Converter#toInteger(String)
     */
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
