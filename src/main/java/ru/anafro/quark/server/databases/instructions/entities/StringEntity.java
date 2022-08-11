package ru.anafro.quark.server.databases.instructions.entities;

public class StringEntity extends InstructionEntity {
    private final String value;

    public StringEntity(String value) {
        super("str");
        this.value = value;
    }

    @Override
    public String toObject() {
        return value;
    }
}
