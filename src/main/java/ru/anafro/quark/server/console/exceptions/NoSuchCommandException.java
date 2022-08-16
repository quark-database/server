package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandLoop;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException(CommandLoop loop, String commandName) {
        super("There is no command with name " + quoted(commandName) + ". Did you mean %s?".formatted(quoted(loop.suggestCommand(commandName))));
    }
}
