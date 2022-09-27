package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

import java.util.Objects;

/**
 * English is a utility class for better looking generated messages.
 * It can determine an article for a noun, ordinal suffixes for a number and so on.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class English {

    /**
     * This private constructor of English class <strong>MUST NOT</strong> be ever
     * called, because English is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private English() {
        throw new CallingUtilityConstructorException(getClass());
    }

    /**
     * Determines an indefinite article for a noun passed to this method.
     *
     * <pre>
     * {@code
     * English.articleFor("dog");   // a
     * English.articleFor("apple"); // an
     * }
     * </pre>
     *
     * @param noun a noun to generate an article for.
     * @return     a generated article.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static String articleFor(String noun) {
        if(Objects.isNull(noun)) {
            throw new QuarkException("Cannot resolve an article for a null string.");
        }

        if(noun.isBlank()) {
            throw new QuarkException("Cannot resolve an article for a blank string. Please, provide a noun, like %s, %s or %s".formatted("dog", "apple", "stick"));
        }

        if(Arrays.contains(new Character[] {'a', 'e', 'i', 'o', 'u'}, Character.toLowerCase(noun.charAt(0)))) {
            return "an";
        } else {
            return "a";
        }
    }

    /**
     * Determines an ordinal suffix for a number (st, nd, rd or th).
     *
     * @param number a number to generate an ordinal suffix for.
     * @return       a determined ordinal suffix.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static String ordinalSuffixFor(int number) {
        return switch(Math.abs(number % 10)) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
