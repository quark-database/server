package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArgument;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class DisableDebugCommand extends Command {
    public DisableDebugCommand() {
        super(new UniqueList<>("disable-debug", "dx"),
                "Disables the debug for a module",
                "Disables the debug for a module with name passed as the 'for' argument",

                new CommandParameter("for", "The module name", "The module name which log level should be switched back to 'info'")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        loop.getCommand("change-log-level").run(new CommandArguments(arguments.getArgument("for"), new CommandArgument("to", LogLevel.INFO.name())));
    }
}
