package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandLoop;
import ru.anafro.quark.server.console.CommandParameters;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class ExitCommand extends Command {
    public ExitCommand(CommandLoop loop) {
        super(loop, new UniqueList<>("exit", "stop", "halt", "end", "quit"),
                "Stops the server",
                "Stops the server so you will need to start it manually by launching 'Start Server.bat'. Note that ongoing processes will be halted."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        logger.error("Stopping the Quark Server...");
        System.exit(0);
    }
}
