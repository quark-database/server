package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandParameterType;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class CommandArgumentIsNotTypeSuitableException extends CommandException {
    public CommandArgumentIsNotTypeSuitableException(String parameterName, String argumentValue, CommandParameterType type) {
        super("The parameter %s with type %s can't contain value %s".formatted(quoted(parameterName), quoted(argumentValue), quoted(type.name())));
    }
}
