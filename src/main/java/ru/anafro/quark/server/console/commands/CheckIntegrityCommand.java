package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.files.FileSystem;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class CheckIntegrityCommand extends Command {
    public CheckIntegrityCommand() {
        super(
                new UniqueList<>(
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
        FileSystem.createDirectoriesIfMissing(
                "Plugins",
                "Databases",
                "Libraries",
                "Commands",
                "Trash",
                "Scripts",
                "Assets"
        );
    }
}
