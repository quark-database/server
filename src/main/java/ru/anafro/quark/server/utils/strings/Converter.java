package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.strings.exceptions.ObjectFormatException;

/**
 * A converter can help you to convert strings to all primitive types
 * you need in one place. We think that Xxx.toXxx() Java syntax is
 * a bit verbose, so we recommend to use this class instead.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Converter {

    /**
     * This private constructor of Converter class <strong>MUST NOT</strong> be ever
     * called, because Converter is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Converter() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Converts a string to an integer. If conversation fails, {@link ObjectFormatException}
     * will be thrown.
     *
     * @param string a string that has to be converted to an integer.
     * @return       the conversation result.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static int toInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Integer.class);
        }
    }

    /**
     * Converts a string to a float. If conversation fails, {@link ObjectFormatException}
     * will be thrown.
     *
     * @param string a string that has to be converted to a float.
     * @return       the conversation result.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static float toFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Float.class);
        }
    }

    /**
     * Converts a string to a long. If conversation fails, {@link ObjectFormatException}
     * will be thrown.
     *
     * @param string a string that has to be converted to a long.
     * @return       the conversation result.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static long toLong(String string) {
        try {
            return Long.parseLong(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Long.class);
        }
    }

    /**
     * Converts a string to a boolean. If conversation fails, {@link ObjectFormatException}
     * will be thrown.
     *
     * @param string a string that has to be converted to a boolean.
     * @return       the conversation result.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static boolean toBoolean(String string) {
        // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't

        try {
            return Boolean.parseBoolean(string);
        } catch(NumberFormatException exception) {  // TODO: Boolean.parseBoolean does not throw NumberFormatException for sure :)
            throw new ObjectFormatException(string, Boolean.class);
        }
    }
}
