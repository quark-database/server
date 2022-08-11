package ru.anafro.quark.server.utils.strings;

public final class Strings {
    private Strings() {
        //
    }

    public static String capitalize(String string) {
        if(string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
