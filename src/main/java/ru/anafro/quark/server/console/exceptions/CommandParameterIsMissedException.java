package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandParameter;

public class CommandParameterIsMissedException extends CommandException {
    public CommandParameterIsMissedException(CommandParameter parameter) {
        super(STR."The parameter \{parameter.name()} is required, but you didn't pass a value for it.");
    }
}
