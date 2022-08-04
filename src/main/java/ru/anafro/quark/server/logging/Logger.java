package ru.anafro.quark.server.logging;

import java.util.Date;

public class Logger {
    private final String label;
    private String format = "[@level @time, @label] @message";

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
        System.out.println(format
            .replace("@level", logLevel.name())
            .replace("@time", new Date().toString())
            .replace("@label", label)
            .replace("@message", message)
        );
    }
}
