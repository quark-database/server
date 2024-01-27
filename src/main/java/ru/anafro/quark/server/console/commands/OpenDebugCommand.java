package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class OpenDebugCommand extends Command {
    public OpenDebugCommand() {
        super(list("open-debug", "show-debug", "open-debug-window", "show-debug-window", "show-debug-window", "show-debug-dialog", "dd"),
                "Opens up a debug dialog",
                "Opens up a debug dialog 'named'",

                new CommandParameter("for", "The name of debug dialog", "The name of debug dialog you want to open")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.debugger(arguments.get("for")).open();
    }
}
