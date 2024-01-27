package ru.anafro.quark.server.utils.integers;

import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.exceptions.UtilityException;

public final class Integers {
    private Integers() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static int countDigits(int integer) {
        int digitCount = 0;

        do {
            integer /= 10;
            digitCount += 1;
        } while (integer != 0);

        return digitCount;
    }

    public static int limit(int integer, int min, int max) {
        if (max <= min) {
            throw new UtilityException("Integers.limit() received max <= min.");
        }

        return Math.max(Math.min(integer, max), min);
    }

    public static int indexLimit(int integer, int length) {
        return limit(integer, 0, length - 1);
    }

    public static int positiveModulus(int integer, int divisor) {
        return ((integer % divisor) + divisor) % divisor;
    }

    public static boolean between(int min, int integer, int max) {
        if (max <= min) {
            throw new UtilityException("Integers.between() received max <= min.");
        }

        return min <= integer && integer <= max;
    }
}
