package ru.anafro.quark.server.logging;

import ru.anafro.quark.server.console.ConsoleColor;

public enum LoggingLevel {
    DEBUG(ConsoleColor.PURPLE), INFO(ConsoleColor.CYAN), WARNING(ConsoleColor.YELLOW), ERROR(ConsoleColor.RED);

    private final ConsoleColor color;

    LoggingLevel(ConsoleColor color) {
        this.color = color;
    }

    public ConsoleColor getColor() {
        return color;
    }
}
