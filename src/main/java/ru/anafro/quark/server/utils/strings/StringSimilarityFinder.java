package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;

/**
 * The string similarity finder is a really handy utility for building
 * a user-friendly interface.
 * <br><br>
 * <p>
 * For example, you have a bunch of commands, and one of the command
 * is named "reload", but the user typed <i>"relaod"</i>. A command
 * with such name does not exist for sure, so instead of saying to the
 * user: "This command does not exist" we can say: "This command does not
 * exist, but we have a command 'reload'. Did you mean this?". Look at the example
 * to know how to use <code>StringSimilarityFinder</code> for such situation:
 *
 * <pre>
 * {@code
 * String[] commands = {"stop", "reload", "download", ...};
 * String requestedCommand = ui.readCommand(); // let's say, "relaod"
 *
 * StringSimilarityFinder.findTheMostSimilar(commands, requestedCommand); // reload
 * }
 * </pre>
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @since Quark 1.1
 */
public final class StringSimilarityFinder {

    /**
     * This private constructor of  class <strong>MUST NOT</strong> be ever
     * called, because  is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private StringSimilarityFinder() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Finds the Levenshtein distance between two strings. You can read more about
     * the Levenshtein distance on Wikipedia here: <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">click to open Wikipedia</a>.
     *
     * @param firstString  the first string.
     * @param secondString the second string.
     * @return the Levenshtein distance between these two strings.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private static int getLevenshteinDistance(String firstString, String secondString) {
        int firstLength = firstString.length();
        int secondLength = secondString.length();

        int[][] table = new int[firstLength + 1][secondLength + 1];

        for (int index = 1; index <= firstLength; index++) {
            table[index][0] = index;
        }

        for (int index = 1; index <= secondLength; index++) {
            table[0][index] = index;
        }

        for (int x = 1; x <= firstLength; x++) {
            for (int y = 1; y <= secondLength; y++) {
                int cost = firstString.charAt(x - 1) == secondString.charAt(y - 1) ? 0 : 1;
                table[x][y] = Integer.min(Integer.min(table[x - 1][y] + 1, table[x][y - 1] + 1), table[x - 1][y - 1] + cost);
            }
        }

        return table[firstLength][secondLength];
    }

    /**
     * Finds the similarity between two strings. 1.0 means that strings are equal.
     * The more the returned number closer to 1.0, the more passed strings are similar.
     * <br><br>
     *
     * <code>"anafro"</code> and <code>"anafor"</code> are quite similar, so this method will return a value close to 1.0 (e.g. 0.97);
     * <br><br>
     *
     * <code>"apple"</code> and <code>"dioxide"</code> are pretty different, so this method will return a value close to 0.0 (e.g. 0.17).
     *
     * @param firstString  the first string.
     * @param secondString the second string
     * @return a value between 0.0 and 1.0 representing the similarity of passed strings.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static double findSimilarity(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            return 0;
        }

        double maxLength = Double.max(firstString.length(), secondString.length());

        if (maxLength > 0) {
            return (maxLength - getLevenshteinDistance(firstString, secondString)) / maxLength;
        }

        return 1;
    }

}
