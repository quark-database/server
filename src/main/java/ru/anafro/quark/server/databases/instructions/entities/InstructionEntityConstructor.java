package ru.anafro.quark.server.databases.instructions.entities;

public abstract class InstructionEntityConstructor {
    private final String name;
    private final InstructionEntityConstructorParameters parameters;

    public InstructionEntityConstructor(String name, InstructionEntityConstructorParameter... parameters) {
        this.name = name;
        this.parameters = new InstructionEntityConstructorParameters(parameters);
    }

    public String getName() {
        return name;
    }

    public InstructionEntityConstructorParameters getParameters() {
        return parameters;
    }

    public abstract InstructionEntity eval(InstructionEntityConstructorArguments arguments);
}
