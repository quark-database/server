package ru.anafro.quark.server.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final String label;
    private String format = "@level @time, @label | @message";
    private LogLevel logFrom = LogLevel.INFO;

    public Logger(String label) {
        this.label = label;
    }

    public Logger() {
        this("Default Logger");
    }

    public Logger(Class<?> clazz) {
        this(clazz.getSimpleName());
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
        if(logLevel.ordinal() >= logFrom.ordinal()) {
            System.out.println(format
                    .replace("@level", logLevel.name())
                    .replace("@time", new SimpleDateFormat("HH:mm").format(new Date()))
                    .replace("@label", label)
                    .replace("@message", message == null ? "<null message>" : message)
            );
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
