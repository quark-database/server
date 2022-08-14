package ru.anafro.quark.server.databases.ql.entities;

import java.util.Objects;

public final class InstructionEntityConstructorParameter {
    private final String name;
    private final String type;
    private final boolean required;

    public InstructionEntityConstructorParameter(String name, String type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public static InstructionEntityConstructorParameter required(String name, String type) {
        return new InstructionEntityConstructorParameter(name, type, true);
    }

    public static InstructionEntityConstructorParameter optional(String name, String type) {
        return new InstructionEntityConstructorParameter(name, type, true);
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public boolean required() {
        return required;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InstructionEntityConstructorParameter) obj;
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
        return "InstructionEntityConstructorParameter[" +
                "name=" + name + ", " +
                "type=" + type + ", " +
                "required=" + required + ']';
    }


}
