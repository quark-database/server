package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

/**
 * The string wrapper utility, which can help you to surround
 * your strings with a string wrapper without repeating it.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Wrapper#wrap(String, String) 
 * @see    Wrapper#quoted(String)
 */
public final class Wrapper {

    /**
     * This private constructor of Wrapper class <strong>MUST NOT</strong> be ever
     * called, because Wrapper is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Wrapper() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Wraps the string with a string wrapper.
     * <br><br>
     *
     * For example, this code will wrap the string with the wrapper:
     * <pre>
     * {@code
     * var happiness = Wrapper.wrap("Happiness", ":)");
     * logger.debug(happiness); // :)Happiness:)
     * }
     * </pre>
     *
     * <i>Tip: This is useful, e.g. when you don't want to repeat the wrapper twice,
     * but creating a separate variable is also not a way. It's also recommended
     * to static-import this method to beautify your code even more.</i>
     *
     * @param  string a string to wrap
     * @param  wrapper a wrapper
     * @return the wrapped string
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Wrapper#quoted
     */
    public static String wrap(String string, String wrapper) {
        return wrapper + string + wrapper;
    }

    /**
     * Wraps the string with a double quote symbol (").
     * <br><br>
     *
     * For example, this method creates quotes:
     * <pre>
     * {@code
     * public static String makeQuote(String quote, String author) {
     *      return "%s -%s".formatted(quoted(quote), author);
     * }
     *
     * // "Doing nothing is better than being busy doing nothing." -Lao Tzu.
     * var quote = makeQuote("Doing nothing is better than being busy doing nothing.", "Lao Tzu");
     * }
     * </pre>
     *
     * <i>Tip: It's also recommended to static-import this method to beautify your code even more.</i>
     *
     * @param  string a string to wrap with quote symbol
     * @return the quote-wrapped string
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Wrapper#wrap
     */
    public static String quoted(String string) {
        return wrap(string, "\"");
    }
}
