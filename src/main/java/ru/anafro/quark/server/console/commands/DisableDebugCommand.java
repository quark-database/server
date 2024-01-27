package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.LoggingLevel;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class DisableDebugCommand extends Command {
    public DisableDebugCommand() {
        super(list("disable-debug", "dx"),
                "Disables the debug for a module",
                "Disables the debug for a module with name passed as the 'for' argument",

                new CommandParameter("for", "The module name", "The module name which log level should be switched back to 'info'")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.runCommand(STR."change-log-level for '\{arguments.get("for")}' to '\{LoggingLevel.INFO.name()}'");
    }
}
