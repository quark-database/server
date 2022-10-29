package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.utils.containers.UniqueList;
import ru.anafro.quark.server.utils.runtime.ApplicationQuitter;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super(new UniqueList<>("reload", "restart", "r"), "Reloads the server", "Stops the Quark Server and starts it again. All the plugins will also be reloaded.");
    }

    @Override
    public void action(CommandArguments arguments) {
        logger.info("Reloading Quark Server...");

        ApplicationQuitter.quit(ApplicationQuitter.ApplicationQuitterStatus.RELOAD);
    }
}
