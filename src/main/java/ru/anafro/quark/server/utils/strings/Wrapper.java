package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;

/**
 * The string wrapper utility, which can help you to surround
 * your strings with a string wrapper without repeating it.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Wrapper#wrap(String, String)
 * @see Wrapper#quoted(String)
 * @since Quark 1.1
 */
public final class Wrapper {

    /**
     * This private constructor of Wrapper class <strong>MUST NOT</strong> be ever
     * called, because Wrapper is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Wrapper() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Wraps the string with a string wrapper.
     * <br><br>
     * <p>
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
     * @param string  a string to wrap
     * @param wrapper a wrapper
     * @return the wrapped string
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Wrapper#quoted
     * @since Quark 1.1
     */
    public static String wrap(String string, String wrapper) {
        return wrapper + string + wrapper;
    }

    /**
     * Wraps the string with a quotation mark.
     * <br><br>
     * <p>
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
     * @param string a string to wrap with quote symbol
     * @return the quote-wrapped string
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Wrapper#wrap
     * @since Quark 1.1
     * @deprecated Since string templates were introduced,
     * all the usages of <code>quoted</code> are redundant.
     */
    @Deprecated(since = "3", forRemoval = true)
    public static String quoted(String string) {
        return wrap(string, "\"");
    }
}
