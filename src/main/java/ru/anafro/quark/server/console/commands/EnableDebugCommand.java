package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class EnableDebugCommand extends Command {
    public EnableDebugCommand() {
        super(new UniqueList<>("enable-debug", "d"),
                "Enables the debug logging for a module",
                "Enables the debug logging for a module with name passed in the 'for' argument",

                new CommandParameter("for", "The module name", "A name of the module which log level should be switched to 'debug'")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.runCommand("change-log-level for '%s' to '%s'".formatted(arguments.get("for"), LogLevel.DEBUG.name()));
    }
}
