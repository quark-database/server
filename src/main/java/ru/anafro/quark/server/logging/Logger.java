package ru.anafro.quark.server.logging;

import ru.anafro.quark.server.console.Console;
import ru.anafro.quark.server.utils.exceptions.Exceptions;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.time.Time;
import ru.anafro.quark.server.utils.types.classes.Classes;

import static ru.anafro.quark.server.utils.arrays.Arrays.array;
import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;

public class Logger {
    private final String label;
    private LoggingLevel logFrom;

    public Logger(String label, LoggingLevel logFrom) {
        this.label = label;
        this.logFrom = logFrom;
    }

    public Logger(Class<?> type, LoggingLevel logFrom) {
        this.label = Classes.getHumanReadableName(type);
        this.logFrom = logFrom;
    }

    public Logger(String label) {
        this(label, LoggingLevel.INFO);
    }

    public Logger(Class<?> clazz) {
        this(clazz, LoggingLevel.INFO);
    }

    public void log(LoggingLevel loggingLevel, String message) {
        if (loggingLevel.ordinal() < logFrom.ordinal()) {
            return;
        }

        String[] lines = byDefault(message, Strings::lines, array("[a null is logged]"));
        for (int i = 0; i < lines.length; i++) {
            var line = lines[i];
            var color = loggingLevel.getColor();
            var level = Strings.mask(loggingLevel.name(), ' ', i != 0);
            var clock = Strings.mask(Time.clock(), ' ', i != 0);
            var label = Strings.mask(this.label, ' ', i != 0);

            Console.println(STR."\{color}\{level}</> <gray>\{clock}   \{label} │</> \{line}");
        }
    }

    public void debug(String message) {
        log(LoggingLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LoggingLevel.INFO, message);
    }

    public void warning(String message) {
        log(LoggingLevel.WARNING, message);
    }

    public void error(String message) {
        log(LoggingLevel.ERROR, message);
    }

    public void error(Throwable throwable) {
        error(Exceptions.format(throwable));
    }

    public void logFrom(LoggingLevel logFrom) {
        this.logFrom = logFrom;
    }
}
