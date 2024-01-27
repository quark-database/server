package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.types.Characters;

import java.util.Objects;

/**
 * English is a utility class for better looking generated messages.
 * It can determine an article for a noun, ordinal suffixes for a number and so on.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @since Quark 1.1
 */
public final class English {

    /**
     * This private constructor of English class <strong>MUST NOT</strong> be ever
     * called, because English is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private English() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Determines an indefinite article for a noun passed to this method.
     * Returns the noun with the article.
     *
     * <pre>
     * {@code
     * English.withArticle("dog");   // a dog
     * English.withArticle("apple"); // an apple
     * }
     * </pre>
     *
     * @param noun a noun to generate an article for.
     * @return a generated article with a noun concatenated.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static String withArticle(String noun) {
        if (Objects.isNull(noun)) {
            throw new QuarkException("Cannot resolve an article for a null string.");
        }

        if (noun.isBlank()) {
            throw new QuarkException("Cannot resolve an article for a blank string. Please, provide a noun, like %s, %s or %s".formatted("dog", "apple", "stick"));
        }

        if (isVowel(noun.charAt(0))) {
            return STR."an \{noun}";
        } else {
            return STR."a \{noun}";
        }
    }

    /**
     * Determines an ordinal suffix for a number (st, nd, rd or th).
     *
     * @param number a number to generate an ordinal suffix for.
     * @return a determined ordinal suffix.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static String ordinalSuffixFor(int number) {
        // TODO: Incorrect for 10..20

        return switch (Math.abs(number % 10)) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public static String demonstrativePronoun(int count) {
        return count == 1 ? "this" : "these";
    }

    public static String pluralize(String noun) {
        var lastCharacter = noun.charAt(noun.length() - 1);

        if (isVowel(lastCharacter)) {
            return STR."\{noun.substring(0, noun.length() - 1)}es";
        }

        if (Characters.equalsIgnoreCase(lastCharacter, 'y')) {
            return STR."\{noun.substring(0, noun.length() - 1)}ies";
        }

        return STR."\{noun}s";
    }

    public static String pluralize(String noun, int count) {
        if (count % 10 == 1) {
            return noun;
        }

        return pluralize(noun);
    }

    private static boolean isVowel(char character) {
        return Arrays.contains(new Character[]{'a', 'e', 'i', 'o', 'u'}, Character.toLowerCase(character));
    }

    public static Object onOrOff(boolean condition) {
        return condition ? "on" : "off";
    }
}
