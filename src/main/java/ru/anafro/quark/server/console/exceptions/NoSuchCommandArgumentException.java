package ru.anafro.quark.server.console.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class NoSuchCommandArgumentException extends CommandException {
    public NoSuchCommandArgumentException(String argumentName) {
        super("Command is trying to access an argument with name " + quoted(argumentName) + ", which does not exist");
    }
}
