package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;

public final class Strings {
    private Strings() {
        throw new CallingUtilityConstructorException(getClass());
    }

    public static String capitalize(String string) {
        if(string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
