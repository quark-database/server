package ru.anafro.quark.server.console;

import ru.anafro.quark.server.console.exceptions.CommandArgumentIsNotTypeSuitableException;
import ru.anafro.quark.server.console.exceptions.InvalidCommandException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandArgumentInParametersException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandParameterException;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class CommandParameters implements Iterable<CommandParameter> {
    private final ArrayList<CommandParameter> parameters;

    public CommandParameters(CommandParameter... parameters) {
        this.parameters = new ArrayList<>(List.of(parameters));
    }

    public void add(CommandParameter parameter) {
        parameters.add(parameter);
    }

    public CommandParameter getParameter(String parameterName) {
        for(var parameter : parameters) {
            if(parameter.name().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public boolean hasParameter(String parameterName) {
        return getParameter(parameterName) != null;
    }

    public CommandParameterType getTypeOf(String parameterName) {
        if(!hasParameter(parameterName)) {
            throw new NoSuchCommandParameterException(parameterName);
        }

        return getParameter(parameterName).type();
    }

    public List<CommandParameter> toList() {
        return parameters;
    }

    @Override
    public Iterator<CommandParameter> iterator() {
        return parameters.iterator();
    }

    public void checkArgumentsValidity(CommandArguments arguments) {
        if(parameters.isEmpty() && !arguments.toList().isEmpty()) {
            throw new InvalidCommandException("The command has no parameters, but following arguments are provided: " + Lists.join(arguments.toList()));
        } else {
            for(var parameter : this) {
                if(!arguments.has(parameter.name()) && parameter.required()) {
                    throw new InvalidCommandException("The parameter %s is required, but you didn't provide it".formatted(quoted(parameter.name())));
                }

                if(arguments.has(parameter.name())) {
                    if(!parameter.type().isValueSuitable(arguments.get(parameter.name()))) {
                        throw new CommandArgumentIsNotTypeSuitableException(parameter.name(), arguments.get(parameter.name()), parameter.type());
                    }
                }
            }

            for(var argument : arguments) {
                if(!this.hasParameter(argument.name())) {
                    throw new NoSuchCommandArgumentInParametersException(this, argument.name());
                }
            }
        }
    }

    public int count() {
        return parameters.size();
    }
}
