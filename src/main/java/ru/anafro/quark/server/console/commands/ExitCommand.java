package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.utils.runtime.ExitCodes;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ExitCommand extends Command {
    public ExitCommand() {
        super(list("exit", "stop", "halt", "end", "quit", "q", "x"),
                "Stops the server",
                "Stops the server so you will need to start it manually by launching 'Start Server.bat'. Note that ongoing processes will be halted."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        logger.info("Stopping the Quark Server...");
        System.exit(ExitCodes.OK);
    }
}
