package ru.anafro.quark.server.database.language;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.Entity;

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
        return general(name, "str");
    }

    public static InstructionParameter required(String name, String type) {
        return new InstructionParameter(name, type, false, false);
    }

    public static InstructionParameter required(String name) {
        return required(name, "str");
    }

    public static InstructionParameter optional(String name, String type) {
        return new InstructionParameter(name, type, true, false);
    }

    public static InstructionParameter optional(String name) {
        return optional(name, "str");
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

    public boolean isNotWildcard() {
        return !isWildcard();
    }

    public boolean canNotReceive(Entity entity) {
        return !entity.getExactTypeName().equals(type);
    }

    public String getSyntaxAsIfGeneral() {
        return STR."<gray>(</><blue>\{type}</>: \{name}<gray>)</>";
    }

    public String getSyntaxAsIfAdditional() {
        return STR."\{name} = <blue>\{type}</>";
    }

    public String formatAsIfGeneral(Entity entity) {
        return entity.format();
    }

    public String format(Entity argument) {
        if (isNotWildcard() && argument.doesntHaveExactType(type)) {
            throw new DatabaseException(STR."Cannot format a parameter \{name} with a \{argument.getType()} = \{argument}.");
        }

        return STR."    <indigo>\{name}</> = \{argument.format()}";
    }
}
