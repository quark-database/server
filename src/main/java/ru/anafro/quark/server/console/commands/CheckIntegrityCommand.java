package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class CheckIntegrityCommand extends Command {
    public CheckIntegrityCommand() {
        super(
                list(
                        "repair",
                        "fix",
                        "mend",
                        "repair-files",
                        "fix-files",
                        "mend-files",
                        "check-integrity"
                ),
                "Fixes the Quark Server files",
                "Scans the Quark Server system files and creates them if needed."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.repairDirectories();
        logger.info("The directories are repaired.");
    }
}
