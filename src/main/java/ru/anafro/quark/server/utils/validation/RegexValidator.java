package ru.anafro.quark.server.utils.validation;

/**
 * The regex validator can be used to create regex validation rules for strings.
 * Pass the regex rule to the constructor to create one. It's also welcomed to
 * extend this class to keep your code solid.
 *
 * <pre>
 * {@code
 * var ipValidator = new RegexValidator("^((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4}$");
 * ipValidator.isValid("127.0.0.1"); // true
 * ipValidator.isValid("anafro");    // false
 * }
 * </pre>
 *
 * @param regex
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Validators#validate(Object, Validator)
 */
public record RegexValidator(String regex) implements Validator<String> {

    /**
     * Validates the passed object. If object passes all the regex
     * rules this validator sets, <code>true</code> will be returned.
     * Otherwise, if object fails the regex validation, <code>false</code> will be returned.
     *
     * @param  value an object to validate
     * @return a boolean represents the validation result (see the description above).
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public boolean isValid(String value) {
        return value.matches(regex);
    }
}
