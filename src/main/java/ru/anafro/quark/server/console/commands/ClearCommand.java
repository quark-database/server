package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.Console;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ClearCommand extends Command {
    public ClearCommand() {
        super(
                list(
                        "clear",
                        "clean",
                        "clr",
                        "cl",
                        "c",
                        "clear-console",
                        "clear-terminal",
                        "clear-screen",
                        "clean-console",
                        "clean-terminal",
                        "clean-screen"
                ),

                "Clears the console",
                "Clears the server's console"
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        Console.clear();
    }
}
