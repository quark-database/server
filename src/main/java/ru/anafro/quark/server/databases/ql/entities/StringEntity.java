package ru.anafro.quark.server.databases.ql.entities;

public class StringEntity extends InstructionEntity {
    private final String value;

    public StringEntity(String value) {
        super("str");
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
