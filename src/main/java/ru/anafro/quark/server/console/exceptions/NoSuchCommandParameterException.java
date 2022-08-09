package ru.anafro.quark.server.console.exceptions;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class NoSuchCommandParameterException extends CommandException {
    public NoSuchCommandParameterException(String parameterName) {
        super("Command is trying to access a parameter with name " + quoted(parameterName) + ", which does not exist");
    }
}
