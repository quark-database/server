package ru.anafro.quark.server.utils.strings;

public final class Wrapper {
    private Wrapper() {
        // Preventing creating utility class instance.
    }

    public static String wrap(String string, String wrapper) {
        return wrapper + string + wrapper;
    }

    public static String quoted(String string) {
        return wrap(string, "\"");
    }
}
