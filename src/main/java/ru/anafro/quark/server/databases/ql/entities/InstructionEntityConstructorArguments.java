package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.ql.parser.exceptions.InstructionParserException;

import java.util.List;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

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

    public void add(InstructionEntityConstructorArgument argument) {
        if(has(argument.getName())) {
            throw new InstructionParserException("An argument with name %s already exists".formatted(quoted(argument.getName())));
        }
    }
}
