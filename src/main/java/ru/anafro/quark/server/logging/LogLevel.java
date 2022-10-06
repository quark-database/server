package ru.anafro.quark.server.logging;

public enum LogLevel {
    DEBUG(Logger.ANSI_WHITE), INFO(Logger.ANSI_CYAN), WARNING(Logger.ANSI_YELLOW), ERROR(Logger.ANSI_RED);

    private String color;

    LogLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
