package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.language.types.EntityType;
import ru.anafro.quark.server.facade.Quark;

public class InstructionEntityConstructorReturnDescription {
    private final EntityType<?> type;
    private final String description;
    private final boolean nullable;

    public InstructionEntityConstructorReturnDescription(String description, String type, boolean nullable) {
        this(description, Quark.types().getOrThrow(type, "We can't find the type '%s' that was used as a return type of the constructor.".formatted(type)), nullable);
    }

    public InstructionEntityConstructorReturnDescription(String description, EntityType<?> type, boolean nullable) {
        this.description = description;
        this.type = type;
        this.nullable = nullable;
    }

    public InstructionEntityConstructorReturnDescription(String description, String type) {
        this(description, type, false);
    }

    public InstructionEntityConstructorReturnDescription(String description, EntityType<?> type) {
        this(description, type, false);
    }

    public static InstructionEntityConstructorReturnDescription returns(String description, String type, boolean nullable) {
        return new InstructionEntityConstructorReturnDescription(description, type, nullable);
    }

    public static InstructionEntityConstructorReturnDescription returns(String description, String type) {
        return new InstructionEntityConstructorReturnDescription(description, type);
    }

    public static InstructionEntityConstructorReturnDescription returns(String description, EntityType<?> type) {
        return new InstructionEntityConstructorReturnDescription(description, type);
    }

    public String getDescription() {
        return description;
    }

    public EntityType<?> getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }
}
