package ru.anafro.quark.server.utils.integers;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

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
}
