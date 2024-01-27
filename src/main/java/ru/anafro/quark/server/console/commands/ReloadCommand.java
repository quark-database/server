package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super(list("reload", "restart", "r"), "Reloads the server", "Stops the Quark Server and starts it again. All the plugins will also be reloaded.");
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.reload();
    }
}
