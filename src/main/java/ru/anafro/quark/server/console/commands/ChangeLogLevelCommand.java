package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandParameter;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.LoggingLevel;
import ru.anafro.quark.server.utils.types.classes.Enums;

import static ru.anafro.quark.server.utils.collections.Collections.list;

public class ChangeLogLevelCommand extends Command {
    public ChangeLogLevelCommand() {
        super(list("change-log-level", "set-log-level", "dl"),
                "Changes the log level for the module",
                "Takes a module with the name passed with 'for' argument and changes it's minimal log level to 'to' argument",
                new CommandParameter("for", "module name", "A module that the minimal logging level should be changed for"),
                new CommandParameter("to", "log level", "A minimal logging level required to log")
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        try {
            var serviceName = arguments.get("for");
            var logLevelName = arguments.get("to").strip().toUpperCase();
            var server = Quark.server();
            var logLevel = LoggingLevel.valueOf(logLevelName);

            var modifyingLogger = switch (serviceName) {
                case "server" -> server.getLogger();
                case "command-parser" -> loop.getParser().getLogger();
                case "lexer" -> server.getLexer().getLogger();
                case "parser" -> server.getParser().getLogger();
                default -> throw new CommandRuntimeException(STR."No such service \{serviceName}");
            };

            modifyingLogger.logFrom(logLevel);

            logger.info(STR."The minimal logger level for \{serviceName} has been switched to \{logLevelName}");
        } catch (IllegalArgumentException exception) {
            error(STR."No such log level '\{arguments.get("to")}'. Please, select some of following: \{Enums.join(LoggingLevel.values())} (not case-sensitive).");
        }
    }
}
