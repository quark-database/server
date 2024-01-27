package ru.anafro.quark.server.console;

public enum ConsoleColor {
    RESET(0),
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    PURPLE(35),
    TEAL(36),
    GRAYISH(37),
    GRAY(90),
    TOMATO(91),
    SALAD(92),
    LEMON(93),
    INDIGO(94),
    MAGENTA(95),
    CYAN(96),
    WHITE(97),
    BOLD(1),
    ITALIC(3);

    private final String ansi;

    ConsoleColor(int ansi) {
        this.ansi = STR."\u001B[\{ansi}m";
    }

    public String getAnsi() {
        return ansi;
    }

    @Override
    public String toString() {
        return ansi;
    }
}
