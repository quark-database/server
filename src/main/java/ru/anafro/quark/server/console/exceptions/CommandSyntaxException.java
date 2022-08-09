package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.utils.strings.StringBuffer;

public class CommandSyntaxException extends CommandException {
    public CommandSyntaxException(String errorMessage, String command, int errorStartingIndex) {
        super(makeMessage(errorMessage, command, errorStartingIndex));
    }

    private static String makeMessage(String errorMessage, String command, int errorStartingIndex) {
        StringBuffer message = new StringBuffer("Command contain a syntax error: ");

        message.appendLine(errorMessage);
        message.appendLine("-".repeat(command.length()));
        message.appendLine(command);
        message.append(" ".repeat(errorStartingIndex));
        message.appendLine("^");
        message.appendLine("-".repeat(command.length()));

        return message.getContent();
    }
}
