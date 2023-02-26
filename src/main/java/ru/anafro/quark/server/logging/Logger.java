package ru.anafro.quark.server.logging;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.debug.CallStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final String label;
    private String format = "@level @time, @label | @message";
    private LogLevel logFrom;

    public Logger(String label, LogLevel logFrom) {
        this.label = label;
        this.logFrom = logFrom;
    }

    public Logger(LogLevel logFrom) {
        this("Unnamed Logger");
        this.logFrom = logFrom;
    }

    public Logger(Class<?> clazz, LogLevel logFrom) {
        this(clazz.getSimpleName());
        this.logFrom = logFrom;
    }

    public Logger(String label) {
        this(label, LogLevel.INFO);
    }

    public Logger() {
        this(LogLevel.INFO);
    }

    public Logger(Class<?> clazz) {
        this(clazz, LogLevel.INFO);
    }

    public String getLabel() {
        return label;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void log(LogLevel logLevel, String message) {
        var lines = message == null ? Arrays.of("<null message>") : message.split("\n");

        if(logLevel.ordinal() >= logFrom.ordinal()) {
            for(var line : lines) {
                Console.synchronizedPrintln(
                        (logLevel == LogLevel.DEBUG ? ANSI_PURPLE + "Logged from: " + CallStack.ofDepth(4) + ANSI_RESET + "\t" : "") +
                        format
                            .replace("@level", logLevel.getColor() + logLevel.name() + ANSI_RESET)
                            .replace("@time", new SimpleDateFormat("HH:mm").format(new Date()))
                            .replace("@label", label)
                            .replace("@message", line)
                );
            }
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void logFrom(LogLevel logFrom) {
        this.logFrom = logFrom;
    }
}
