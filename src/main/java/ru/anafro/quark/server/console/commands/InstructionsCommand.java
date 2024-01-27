package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class InstructionsCommand extends Command {
    public InstructionsCommand() {
        super(list("instructions", "list-instructions", "instruction-list", "existing-instructions", "list-existing-instructions", "available-instructions", "il"), "Shows all existing instructions", "Shows all the instructions available in this version of Quark QL");
    }

    @Override
    public void action(CommandArguments arguments) {
        for (var instruction : Quark.instructions()) {
            logger.info(instruction.getSyntax());
        }
    }
}
