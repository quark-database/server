package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.exceptions.UtilityException;

public final class StringSimilarityFinder {
    private StringSimilarityFinder() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static int getLevenshteinDistance(String firstString, String secondString)
    {
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
                int cost = firstString.charAt(x - 1) == secondString.charAt(y - 1) ? 0: 1;
                table[x][y] = Integer.min(Integer.min(table[x - 1][y] + 1, table[x][y - 1] + 1), table[x - 1][y - 1] + cost);
            }
        }

        return table[firstLength][secondLength];
    }

    public static double findSimilarity(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            throw new UtilityException("findSimilarity(String, String) requires two not-null strings, but one or two of them is/are null");
        }

        double maxLength = Double.max(firstString.length(), secondString.length());

        if (maxLength > 0) {
            return (maxLength - getLevenshteinDistance(firstString, secondString)) / maxLength;
        }

        return 1.0;
    }

    public static int compare(String originalString, String firstString, String secondString) {
        if(firstString.equals(secondString)) {
            return 0;
        }

        return StringSimilarityFinder.findSimilarity(originalString, firstString) > StringSimilarityFinder.findSimilarity(originalString, secondString) ? 1 : -1;
    }
}
