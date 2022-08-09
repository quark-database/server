package ru.anafro.quark.server.console.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class CommandWithThisNameAlreadyExistsException extends CommandException {
    public CommandWithThisNameAlreadyExistsException(String registeringCommandName) {
        super("You can't register a new command with name %s, because it one of the registered commands has this name".formatted(quoted(registeringCommandName)));
    }
}
