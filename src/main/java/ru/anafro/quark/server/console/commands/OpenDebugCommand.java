package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.*;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.debug.ui.InstructionLexerDebugFrame;
import ru.anafro.quark.server.utils.containers.UniqueList;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class OpenDebugCommand extends Command {
    public OpenDebugCommand(CommandLoop loop) {
        super(loop, new UniqueList<>("open-debug", "show-debug", "open-debug-window", "show-debug-window", "show-debug-window", "show-debug-dialog"),
                "Opens up a debug dialog",
                "Opens up a debug dialog 'named'",

                new CommandParameter("named", "The name of debug dialog", "The name of debug dialog you want to open")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        var debugDialog = switch(arguments.get("named")) {
            case "instruction-lexer" -> new InstructionLexerDebugFrame(loop.getServer());
            default -> throw new CommandRuntimeException("No such debug dialog named " + quoted(arguments.get("named")));
        };

        logger.info("Opening the %s debug dialog...".formatted(arguments.get("named")));
        debugDialog.open();
    }
}
