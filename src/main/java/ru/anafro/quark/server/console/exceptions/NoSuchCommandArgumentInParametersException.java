package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandParameters;
import ru.anafro.quark.server.utils.containers.Lists;

public class NoSuchCommandArgumentInParametersException extends CommandException {
    public NoSuchCommandArgumentInParametersException(CommandParameters parameters, String argumentName) {
        super("Command parameters %s doesn't contain parameter %s, but it was provided".formatted(Lists.join(parameters.toList()), argumentName));
    }
}
