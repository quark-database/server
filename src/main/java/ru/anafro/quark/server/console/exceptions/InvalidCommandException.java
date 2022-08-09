package ru.anafro.quark.server.console.exceptions;

public class InvalidCommandException extends CommandException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
