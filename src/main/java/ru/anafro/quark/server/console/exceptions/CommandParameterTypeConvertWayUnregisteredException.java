package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandArgument;
import ru.anafro.quark.server.console.CommandParameterType;

public class CommandParameterTypeConvertWayUnregisteredException extends CommandException {
    public CommandParameterTypeConvertWayUnregisteredException(CommandArgument argument, CommandParameterType type) {
        super("The command tried to convert an existing argument '" + argument.name() + "' to type '" + type + "', which is ok, but there is no way in .as() method to convert the string value of the argument. Please, add a new switch case in .as() method of CommandArgument to let us now how to convert the string to this type.");
    }
}
