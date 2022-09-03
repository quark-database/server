package ru.anafro.quark.server.databases.ql;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public class InstructionParameter {
    private final String name;
    private final String type;
    private final boolean general;
    private final boolean optional;

    public InstructionParameter(String name, String type, boolean optional, boolean general) {
        this.general = general;
        this.name = name;
        this.type = type;
        this.optional = optional;
    }

    public InstructionParameter(String name, String type, boolean optional) {
        this(name, type, optional, false);
    }

    public static InstructionParameter general(String name, String type) {
        return new InstructionParameter(name, type, false, true);
    }

    public static InstructionParameter general(String name) {
        return general(name, Types.STRING);
    }

    public static InstructionParameter required(String name, String type) {
        return new InstructionParameter(name, type, false, false);
    }

    public static InstructionParameter required(String name) {
        return required(name, Types.STRING);
    }

    public static InstructionParameter optional(String name, String type) {
        return new InstructionParameter(name, type, true, false);
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

    public boolean isWildcard() {
        return type.equals(Entity.WILDCARD_TYPE);
    }

    public boolean isAdditional() {
        return !isGeneral();
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
