package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.facade.Quark;

/**
 * Thrown when command entered into command line is not found.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class NoSuchCommandException extends CommandException {

    /**
     * Creates a new exception instance.
     *
     * @param commandName the command name that was not found.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public NoSuchCommandException(String commandName) {
        super(STR."There is no command with name '\{commandName}'. Did you mean '\{Quark.commands().suggestName(commandName)}'?");

        Quark.commandLoop().setFailedCommand(Quark.commandLoop().getParser().getCommandName());
        Quark.commandLoop().setFailedArguments(Quark.commandLoop().getParser().getArguments());
    }
}
