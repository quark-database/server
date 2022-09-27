package ru.anafro.quark.server.console.commands;

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
    public void action(CommandArguments arguments) { // TODO: Extract argument 'for' to a variable
        var debugDialog = switch(arguments.get("for")) {
            case "lexer" -> new InstructionLexerDebugFrame();
            case "parser" -> new InstructionParserDebugFrame();
            case "constructors" -> new EntityConstructorDebugFrame();
            default -> throw new CommandRuntimeException("No such debug dialog named " + quoted(arguments.get("for")));
        };

        logger.info("Opening the %s debug dialog...".formatted(arguments.get("for")));
        debugDialog.open();
    }
}
