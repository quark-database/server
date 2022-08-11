package ru.anafro.quark.server.databases.instructions.entities;

import java.util.List;

public class InstructionEntityConstructorArguments {
    private final List<InstructionEntityConstructorArgument> arguments;

    public InstructionEntityConstructorArguments(InstructionEntityConstructorArgument... arguments) {
        this.arguments = List.of(arguments);
    }

    public boolean has(String argumentName) {
        return getArgument(argumentName) != null;
    }

    public InstructionEntityConstructorArgument getArgument(String argumentName) {
        for(var argument : arguments) {
            if(argument.getName().equals(argumentName)) {
                return argument;
            }
        }

        return null;
    }
}
