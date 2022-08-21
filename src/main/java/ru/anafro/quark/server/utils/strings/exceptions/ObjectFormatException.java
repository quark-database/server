package ru.anafro.quark.server.utils.strings.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.strings.Converter;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

/**
 * An {@link ObjectFormatException} should be thrown only from {@link Converter}
 * class to indicate that string cannot be converted to some type.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Converter
 */
public class ObjectFormatException extends QuarkException {

    /**
     * Creates a new instance of this exception.
     *
     * @param string a string that was tried to be converted to the specified class.
     * @param clazz  a class of type that this string was tried to be converted to.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    ObjectFormatException
     */
    public ObjectFormatException(String string, Class<?> clazz) {
        super("String " + quoted(string) + " is being tried to be converted to type " + quoted(clazz.getSimpleName()) + ", but it is not in a good format.");
    }
}
