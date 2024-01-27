package ru.anafro.quark.server.console;

import ru.anafro.quark.server.utils.strings.Converter;

import java.util.Objects;

public final class CommandArgument {
    private final String name;
    private String value;

    public CommandArgument(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Object as(CommandParameterType type) {
        return switch (type) {
            case STRING -> value;
            case INTEGER -> Converter.toInteger(value);
            case FLOAT -> Converter.toFloat(value);
            case BOOLEAN -> Converter.toBoolean(value);
        };
    }

    public boolean named(String name) {
        return name.equals(this.name());
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CommandArgument) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return STR."CommandArgument[name=\{name}, value=\{value}\{']'}";
    }
}
