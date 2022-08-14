package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.*;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.utils.containers.UniqueList;

import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ChangeLogLevelCommand extends Command {
    public ChangeLogLevelCommand() {
        super(new UniqueList<>("change-log-level", "set-log-level"),
            "Changes the log level for the module",
            "Takes a module with the name passed with 'for' argument and changes it's minimal log level to 'to' argument",
            new CommandParameter("for", "module name", "A module that the minimal logging level should be changed for"),
            new CommandParameter("to", "log level", "A minimal logging level required to log")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        try {
            var level = LogLevel.valueOf(arguments.get("to").strip().toUpperCase());

            var logger = switch (arguments.get("for")) {
                case "server" -> loop.getServer().getLogger();
                case "command-parser" -> loop.getParser().getLogger();
                case "instruction-lexer" -> loop.getServer().getInstructionLexer().getLogger();
                default -> throw new CommandRuntimeException("No such logging object " + quoted(arguments.get("to")));
            };

            logger.logFrom(level);

            this.logger.info("The minimal logger level for %s has been switched to %s".formatted(quoted(arguments.get("for")), quoted(arguments.get("to"))));
        } catch(IllegalArgumentException exception) {
            error("No such log level " + quoted(arguments.get("to")) + ". Please, select some of following: " + Arrays.stream(LogLevel.values()).map(Enum::name).collect(Collectors.joining(", ")) + " (not sensitive to case).");
        }
    }
}
