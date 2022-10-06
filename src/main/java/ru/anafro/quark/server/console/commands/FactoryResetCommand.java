package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class FactoryResetCommand extends Command {
    public FactoryResetCommand() {
        super(
                new UniqueList<>("factory-reset", "back-to-factory-settings"),
                "Resets the server to factory settings.",
                "Resets all the settings and databases to the before-use state."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.factoryReset();
    }
}
