package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class InstallCommand extends Command {
    public InstallCommand() {
        super(
                list("install"),
                "This command is run after installation of Quark. Do not run manually",
                """
                        Creates all the necessary databases and tables for Quark Server to work.
                        This command must never be called manually.
                        """
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Quark.install();
    }
}
