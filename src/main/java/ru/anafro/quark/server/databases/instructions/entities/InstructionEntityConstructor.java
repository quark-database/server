package ru.anafro.quark.server.databases.instructions.entities;

public abstract class InstructionEntityConstructor<T extends InstructionEntity<?>> {
    private final String name;
    private final InstructionEntityConstructorParameters parameters;

    public InstructionEntityConstructor(String name, InstructionEntityConstructorParameter... parameters) {
        super();
        this.name = name;
        this.parameters = new InstructionEntityConstructorParameters(parameters);
    }

    public String getName() {
        return name;
    }

    public InstructionEntityConstructorParameters getParameters() {
        return parameters;
    }

    private abstract T evaluate(/* Add arguments here */);

    public T invoke() {
        //
    }
}
