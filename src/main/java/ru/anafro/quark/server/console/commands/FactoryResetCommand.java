package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class FactoryResetCommand extends Command {
    public FactoryResetCommand() {
        super(
                list("factory-reset", "back-to-factory-settings"),
                "Resets the server to factory settings.",
                "Resets all the settings and databases to the before-use state."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.factoryReset();
    }
}
