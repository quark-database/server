package ru.anafro.quark.server.utils.types;

import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public final class Booleans {
    private static final String[] TRUTHY_STRING_VALUES = {"yes", "true", "+", "1", "y"};
    private static final String[] FALSY_STRING_VALUES = {"no", "false", "-", "0", "n"};


    private Booleans() {
    }

    public static boolean canBeCreatedFromString(String string) {
        return Arrays.contains(TRUTHY_STRING_VALUES, string) || Arrays.contains(FALSY_STRING_VALUES, string);
    }

    public static boolean createFromString(String string) {
        if (Arrays.contains(TRUTHY_STRING_VALUES, string)) {
            return true;
        }

        if (Arrays.contains(FALSY_STRING_VALUES, string)) {
            return false;
        }

        throw new TypeException(STR."\{string} is nor falsy and truthy.");
    }
}
