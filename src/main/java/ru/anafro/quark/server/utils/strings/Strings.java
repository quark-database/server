package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

import java.util.stream.Collector;

/**
 * Strings is an uncategorized utility bundle for string manipulations.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Strings {

    /**
     * This private constructor of "Strings" class <strong>MUST NOT</strong> be ever
     * called, because "Strings" is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Wrapper
     */
    private Strings() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Capitalizes the string.
     *
     * <pre>
     * {@code
     * Strings.capitalize("anafro");        // Anafro
     * Strings.capitalize("");              // ""
     * Strings.capitalize("hello world!");  // Hello world!
     * }
     * </pre>
     *
     * @param string a string to capitalize.
     * @return       a capitalized string.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static String capitalize(String string) {
        if(string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String invertCase(String string) {
        return string
                .chars()
                .boxed()
                .map(character -> Character.isLowerCase(character) ? Character.toUpperCase(character) : Character.toLowerCase(character))
                .collect(Collector.of(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append, StringBuilder::toString));
    }
}
