package ru.anafro.quark.server.language.entities;

import java.util.Objects;

public final class InstructionEntityConstructorParameter {
    public static final String VARARGS_TYPE_MARKER = "...";
    private final boolean required;
    private final String name;
    private final String type;
    private final boolean varargs;

    public InstructionEntityConstructorParameter(String name, String type, boolean required) {
        if (type.endsWith(VARARGS_TYPE_MARKER)) {
            this.varargs = true;
            this.type = type.substring(0, type.length() - VARARGS_TYPE_MARKER.length());
        } else {
            this.varargs = false;
            this.type = type;
        }

        this.name = name;
        this.required = required;
    }

    public static InstructionEntityConstructorParameter required(String name, String type) {
        return new InstructionEntityConstructorParameter(name, type, true);
    }

    public static InstructionEntityConstructorParameter varargs(String name, String varargType) {
        return InstructionEntityConstructorParameter.required(name, varargType + VARARGS_TYPE_MARKER);
    }

    public static InstructionEntityConstructorParameter optional(String name, String type) {
        return new InstructionEntityConstructorParameter(name, type, false);
    }

    public boolean isRequired() {
        return required;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InstructionEntityConstructorParameter) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type) &&
                this.required == that.isRequired();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, required);
    }

    @Override
    public String toString() {
        return STR."InstructionEntityConstructorParameter{name='\{name}\{'\''}, type='\{type}\{'\''}, required=\{required}, varargs=\{varargs}\{'}'}";
    }

    public boolean isVarargs() {
        return varargs;
    }

    public boolean isWildcard() {
        return type.equals(Entity.WILDCARD_TYPE);
    }

}
