package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class CommandException extends QuarkException {
    public CommandException(String message) {
        super(message);
    }
}
