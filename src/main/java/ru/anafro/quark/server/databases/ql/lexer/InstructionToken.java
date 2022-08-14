package ru.anafro.quark.server.databases.ql.lexer;

public abstract class InstructionToken {
    private final String name, value;

    public InstructionToken(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean is(String name) {
        return name.equals(this.name);
    }

    public abstract String getPresentation();

    public abstract boolean isValueValid(String value);

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
