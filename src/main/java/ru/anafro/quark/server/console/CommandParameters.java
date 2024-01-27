package ru.anafro.quark.server.console;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.console.exceptions.CommandArgumentHasWrongTypeException;
import ru.anafro.quark.server.console.exceptions.CommandDoesntHaveParametersException;
import ru.anafro.quark.server.console.exceptions.CommandParameterIsMissedException;
import ru.anafro.quark.server.console.exceptions.UnknownCommandParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandParameters implements Iterable<CommandParameter> {
    private final ArrayList<CommandParameter> parameters;

    public CommandParameters(CommandParameter... parameters) {
        this.parameters = new ArrayList<>(List.of(parameters));
    }

    public void add(CommandParameter parameter) {
        parameters.add(parameter);
    }

    public CommandParameter getParameter(String parameterName) {
        for (var parameter : parameters) {
            if (parameter.name().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean doesntHaveParameter(String parameterName) {
        return getParameter(parameterName) == null;
    }

    public List<CommandParameter> toList() {
        return parameters;
    }

    @NotNull
    @Override
    public Iterator<CommandParameter> iterator() {
        return parameters.iterator();
    }

    public void ensureArgumentsAreValid(CommandArguments arguments) {
        if (parameters.isEmpty() && arguments.isNotEmpty()) {
            throw new CommandDoesntHaveParametersException(arguments);
        }

        for (var parameter : parameters) {
            arguments.tryGet(parameter.name()).ifPresentOrElse(argument -> {
                if (parameter.canNotContain(argument)) {
                    throw new CommandArgumentHasWrongTypeException(parameter, argument);
                }
            }, () -> {
                if (parameter.isRequired()) {
                    throw new CommandParameterIsMissedException(parameter);
                }
            });
        }

        arguments.stream().filter(this::doesntHaveParameter).findFirst().ifPresent(argument -> {
            throw new UnknownCommandParameterException(parameters, argument);
        });
    }

    private boolean doesntHaveParameter(CommandArgument argument) {
        return doesntHaveParameter(argument.name());
    }

    public int count() {
        return parameters.size();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
