package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.LoggingLevel;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class EnableDebugCommand extends Command {
    public EnableDebugCommand() {
        super(list("enable-debug", "d"),
                "Enables the debug logging for a module",
                "Enables the debug logging for a module with name passed in the 'for' argument",

                new CommandParameter("for", "The module name", "A name of the module which log level should be switched to 'debug'")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.runCommand(STR."change-log-level for '\{arguments.get("for")}' to '\{LoggingLevel.DEBUG.name()}'");
    }
}
