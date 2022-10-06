package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class OpenDebugCommand extends Command {
    public OpenDebugCommand() {
        super(new UniqueList<>("open-debug", "show-debug", "open-debug-window", "show-debug-window", "show-debug-window", "show-debug-dialog", "dd"),
                "Opens up a debug dialog",
                "Opens up a debug dialog 'named'",

                new CommandParameter("for", "The name of debug dialog", "The name of debug dialog you want to open")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        if(Quark.debugFrames().missing(arguments.get("for"))) {
            error("No such debug frame. Did you mean %s?".formatted(Quark.debugFrames().suggest(arguments.get("for")).getName()));
        }

        var frame = Quark.debugFrames().get(arguments.get("for"));

        if(frame.isEnabled()) {
            logger.info("Debug dialog %s is already opened. Check out your taskbar.".formatted(frame.getName()));
        } else {
            logger.info("Opening the %s debug dialog...".formatted(frame.getName()));
            frame.open();
        }
    }
}
