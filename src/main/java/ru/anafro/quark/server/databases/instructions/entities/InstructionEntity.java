package ru.anafro.quark.server.databases.instructions.entities;

public abstract class InstructionEntity<T> {
    private final String name;

    public InstructionEntity(String name) {
        this.name = name;
    }

    public abstract T toObject();

    public String getName() {
        return name;
    }
}
