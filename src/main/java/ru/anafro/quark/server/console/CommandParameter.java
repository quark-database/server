package ru.anafro.quark.server.console;

import java.util.Objects;

public final class CommandParameter {
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private final CommandParameterType type;
    private final boolean required;

    public CommandParameter(String name, String shortDescription, String longDescription, CommandParameterType type, boolean required) {
        this.name = name;
        this.type = type;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.required = required;
    }

    public CommandParameter(String name, String shortDescription, String longDescription, CommandParameterType type) {
        this(name, shortDescription, longDescription, type, true);
    }

    public CommandParameter(String name, String shortDescription, String longDescription) {
        this(name, shortDescription, longDescription, CommandParameterType.STRING);
    }

    public CommandParameter(String name, String shortDescription, String longDescription, boolean required) {
        this(name, shortDescription, longDescription, CommandParameterType.STRING, required);
    }

    public String name() {
        return name;
    }

    public String shortDescription() {
        return shortDescription;
    }

    public String longDescription() {
        return longDescription;
    }

    public CommandParameterType type() {
        return type;
    }

    public boolean required() {
        return required;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CommandParameter) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type) &&
                this.required == that.required;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, required);
    }

    @Override
    public String toString() {
        return "CommandParameter[" +
                "name=" + name + ", " +
                "type=" + type + ", " +
                "required=" + required + ']';
    }
}
