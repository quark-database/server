package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandArguments;

public class CommandDoesntHaveParametersException extends CommandException {

    public CommandDoesntHaveParametersException(CommandArguments arguments) {
        super(STR."The command doesn't have parameters, but some were passed: \{arguments}.");
    }
}
