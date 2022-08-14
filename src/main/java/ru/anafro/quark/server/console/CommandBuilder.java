package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandMustHaveAtLeastOneNameException;
import ru.anafro.quark.server.utils.containers.UniqueList;
import ru.anafro.quark.server.utils.patterns.Builder;

import java.util.function.BiConsumer;

public class CommandBuilder implements Builder<Command> {
    private final UniqueList<String> names;
    private String shortDescription;
    private String longDescription;
    private final CommandParameters parameters;
    private BiConsumer<CommandLoop, CommandArguments> action;

    public CommandBuilder() {
        this.names = new UniqueList<>();
        this.parameters = new CommandParameters();
    }

    public CommandBuilder name(String newName) {
        names.add(newName);

        return this;
    }

    public CommandBuilder shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public CommandBuilder longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public CommandBuilder action(BiConsumer<CommandLoop, CommandArguments> action) {
        this.action = action;

        return this;
    }

    public CommandBuilder parameter(String name, String shortDescription, String longDescription, CommandParameterType type, boolean required) {
        parameters.add(new CommandParameter(name, shortDescription, longDescription, type, required));

        return this;
    }

    public CommandBuilder required(String name, String shortDescription, String longDescription, CommandParameterType type) {
        return parameter(name, shortDescription, longDescription, type, true);
    }

    public CommandBuilder required(String name, String shortDescription, String longDescription) {
        return required(name, shortDescription, longDescription, CommandParameterType.STRING);
    }

    public CommandBuilder requiredString(String name, String shortDescription, String longDescription) {
        return required(name, shortDescription, longDescription);
    }

    public CommandBuilder requiredInteger(String name, String shortDescription, String longDescription) {
        return required(name, shortDescription, longDescription, CommandParameterType.INTEGER);
    }

    public CommandBuilder requiredBoolean(String name, String shortDescription, String longDescription) {
        return required(name, shortDescription, longDescription, CommandParameterType.BOOLEAN);
    }

    public CommandBuilder requiredFloat(String name, String shortDescription, String longDescription) {
        return required(name, shortDescription, longDescription, CommandParameterType.FLOAT);
    }

    public CommandBuilder optional(String name, String shortDescription, String longDescription, CommandParameterType type) {
        return parameter(name, shortDescription, longDescription, type, false);
    }

    public CommandBuilder optional(String name, String shortDescription, String longDescription) {
        return optional(name, shortDescription, longDescription, CommandParameterType.STRING);
    }

    public CommandBuilder optionalString(String name, String shortDescription, String longDescription) {
        return optional(name, shortDescription, longDescription);
    }

    public CommandBuilder optionalInteger(String name, String shortDescription, String longDescription) {
        return optional(name, shortDescription, longDescription, CommandParameterType.INTEGER);
    }

    public CommandBuilder optionalBoolean(String name, String shortDescription, String longDescription) {
        return optional(name, shortDescription, longDescription, CommandParameterType.BOOLEAN);
    }

    public CommandBuilder optionalFloat(String name, String shortDescription, String longDescription) {
        return optional(name, shortDescription, longDescription, CommandParameterType.FLOAT);
    }

    @Override
    public Command build() {
        if(names.isEmpty()) {
            throw new CommandMustHaveAtLeastOneNameException();
        }

        return new Command(names, shortDescription, longDescription, parameters) {
            @Override
            public void action(CommandArguments arguments) {
                if(action != null) {
                    action.accept(loop, arguments);
                }
            }
        };
    }
}
