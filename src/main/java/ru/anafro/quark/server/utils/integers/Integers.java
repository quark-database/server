package ru.anafro.quark.server.utils.integers;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.exceptions.UtilityException;

public final class Integers {
    private Integers() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static int countDigits(int integer) {
        int digitCount = 0;

        while(integer != 0) {
            integer /= 10;
            digitCount += 1;
        }

        return digitCount;
    }

    public static int limit(int integer, int min, int max) {
        if(max <= min) {
            throw new UtilityException("Integers.limit() received max <= min.");
        }

        return Math.max(Math.min(integer, max), min);
    }
}
