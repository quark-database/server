package ru.anafro.quark.server.console.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException(String commandName) {
        super("There is no command with name " + quoted(commandName));
    }
}
