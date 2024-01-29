package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collector;

/**
 * Strings is an uncategorized utility bundle for string manipulations.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public final class Strings {
    private static final int INDEX_OF_NOT_FOUND_STATUS = -1;

    /**
     * This private constructor of "Strings" class <strong>MUST NOT</strong> be ever
     * called, because "Strings" is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Wrapper
     * @since Quark 1.1
     */
    private Strings() {
        throw new UtilityClassInstantiationException(getClass());
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
     * @return a capitalized string.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static String capitalize(String string) {
        if (string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    /**
     * Inverts the case of the string.
     *
     * <pre>
     * {@code
     * String.invertCase("Anafro!"); // aNAFRO!
     * }
     * </pre>
     *
     * @param string the string to invert.
     * @return the inverted string.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static String invertCase(String string) {
        return string
                .chars()
                .boxed()
                .map(character -> Character.isLowerCase(character) ? Character.toUpperCase(character) : Character.toLowerCase(character))
                .collect(Collector.of(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append, StringBuilder::toString));
    }

    /**
     * Counts entries of one string inside another.
     * If any of the parameters is {@code null}, {@code 0} will be returned.
     *
     * @param whereToCount the string where to search.
     * @param whatToCount  the string to search.
     * @return the count of entries inside the string passed.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static int countEntries(String whereToCount, String whatToCount) {
        if (whereToCount == null || whatToCount == null) {
            return 0;
        }

        return whereToCount.split(Pattern.quote(whatToCount)).length - 1;
    }

    public static String[] lines(String string) {
        return string.split("\n");
    }

    public static String[] words(String string) {
        return string.split(" +");
    }

    public static Optional<Integer> lastIndexOf(String string, char character) {
        var lastIndex = string.lastIndexOf(character);

        return Optional.ofNullable(lastIndex == INDEX_OF_NOT_FOUND_STATUS ? null : lastIndex);
    }

    public static String convertPascalCaseToCapitalizedCase(String pascalCase) {
        var buffer = new TextBuffer();

        for (char character : pascalCase.toCharArray()) {
            if (buffer.isNotEmpty() && Character.isUpperCase(character)) {
                buffer.append(' ');
            }

            buffer.append(character);
        }

        return buffer.getContent();
    }

    public static String removeTrailing(String string, String suffix) {
        if (!string.endsWith(suffix)) {
            return string;
        }

        return string.substring(0, string.length() - suffix.length());
    }

    public static String removeLeading(String string, String prefix) {
        if (!string.startsWith(prefix)) {
            return string;
        }

        return string.substring(prefix.length());
    }

    public static String leftTrim(String string) {
        return string.replaceAll("^\\s+", "");
    }

    public static String rightTrim(String string) {
        return string.replaceAll("\\s+$", "");
    }

    public static String reverse(String string) {
        return new StringBuffer(string).reverse().toString();
    }

    public static String mask(String string, char mask, boolean condition) {
        return condition ? String.valueOf(mask).repeat(string.length()) : string;
    }

    public static String spaces(int count) {
        return " ".repeat(Math.max(0, count));
    }

    private static String ellipsis(String string, int width) {
        var ellipsis = "...";

        if (string.length() <= width) {
            return string;
        }

        return string.substring(0, string.length() - ellipsis.length()) + ellipsis;
    }

    public static String padCenter(String string, int width) {
        var padding = Math.max(0, width - string.length());
        var leftPadding = " ".repeat(Math.ceilDiv(padding, 2));
        var rightPadding = " ".repeat(Math.floorDiv(padding, 2));

        return leftPadding + ellipsis(string, width) + rightPadding;
    }

    public static boolean startsWithAny(String string, String... prefixes) {
        for (var prefix : prefixes) {
            if (string.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    public static String getLastToken(String string, String delimiter) {
        return Arrays.getLast(string.split(Pattern.quote(delimiter)));
    }

    public static String breakWords(String string, int maxCharactersPerLine) {
        var words = words(string);
        var result = new TextBuffer();
        var charactersInLine = 0;

        for (var word : words) {
            result.append(word, ' ');
            charactersInLine += word.length();

            if (charactersInLine >= maxCharactersPerLine) {
                result.nextLine();
                charactersInLine = 0;
            }
        }

        return result.getContent();
    }
}
