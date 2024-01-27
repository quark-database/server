package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ConstructorsCommand extends Command {
    public ConstructorsCommand() {
        super(list("constructors", "list-constructors", "constructor-list", "existing-constructors", "list-existing-constructors", "available-constructors", "cl"), "Shows all existing constructors", "Shows all the constructors available in this version of Quark QL");
    }

    @Override
    public void action(CommandArguments arguments) {
        for (var constructor : Quark.constructors()) {
            logger.info(constructor.getSyntax());
        }
    }
}
