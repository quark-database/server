package ru.anafro.quark.server.console.exceptions;

public class CommandMustHaveAtLeastOneNameException extends CommandException {
    public CommandMustHaveAtLeastOneNameException() {
        super(".build() was called from a command builder, but there are no names for a command. Please, provide one by adding 'builder.name(\"command name\")'");
    }
}
