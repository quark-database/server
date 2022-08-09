package ru.anafro.quark.server.utils.strings;

import ru.anafro.quark.server.utils.strings.exceptions.ObjectFormatException;

public final class Converter {
    private Converter() {
        // Preventing creating utility class instance.
    }

    public static int toInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Integer.class);
        }
    }

    public static float toFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Float.class);
        }
    }

    public static long toLong(String string) {
        try {
            return Long.parseLong(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Long.class);
        }
    }

    public static boolean toBoolean(String string) {
        try {
            return Boolean.parseBoolean(string);
        } catch(NumberFormatException exception) {
            throw new ObjectFormatException(string, Boolean.class);
        }
    }
}
