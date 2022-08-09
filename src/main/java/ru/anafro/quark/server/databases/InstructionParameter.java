package ru.anafro.quark.server.databases;

import ru.anafro.quark.server.databases.exceptions.QuerySyntaxException;

public class InstructionParameter {
    private String value; // TODO: remove
    private final String name;
    private final String type;
    private final boolean general;
    private final boolean optional;

    public InstructionParameter(String name, String type, String defaultValue, boolean optional, boolean general) {
        this.general = general;
        this.name = name;
        this.type = type;
        this.value = defaultValue;
        this.optional = optional;
    }

    public InstructionParameter(String name, String type, String defaultValue, boolean optional) {
        this(name, type, defaultValue, optional, false);
    }

    public static InstructionParameter general(String name, String type) {
        return new InstructionParameter(name, type, "", false, true);
    }

    public static InstructionParameter general(String name) {
        return general(name, Types.STRING);
    }

    public static InstructionParameter required(String name, String type, String defaultValue) {
        return new InstructionParameter(name, type, defaultValue, false);
    }

    public static InstructionParameter required(String name, String type) {
        return required(name, type, null);
    }

    public static InstructionParameter required(String name) {
        return required(name, Types.STRING);
    }

    public static InstructionParameter optional(String name, String type, String defaultValue) {
        return new InstructionParameter(name, type, defaultValue, true);
    }

    public static InstructionParameter optional(String name, String type) {
        return required(name, type, null);
    }

    public static InstructionParameter optional(String name) {
        return required(name, Types.STRING);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    public boolean isOptional() {
        return optional;
    }

    public boolean isRequired() {
        return !isOptional();
    }

    public boolean isGeneral() {
        return general;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int asInt() {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException exception) {
            throw new QuerySyntaxException("Instruction parameter %s should be integer, but '%s' has gotten".formatted(name, value));
        }
    }

    public interface Types {
        String STRING = "str";
        String INT = "int";
        String FLOAT = "float";
        String BOOLEAN = "boolean";
        String COLUMN = "column";
        String CONDITION = "condition";
        String CHANGER = "changer";
    }
}
