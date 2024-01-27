package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;

public class CommandFinishedEvent extends Event {
    private final Command command;
    private final CommandArguments arguments;

    public CommandFinishedEvent(Command command, CommandArguments arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public Command getCommand() {
        return command;
    }

    public CommandArguments getArguments() {
        return arguments;
    }
}
