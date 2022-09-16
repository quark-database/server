package ru.anafro.quark.server.console.exceptions;

import ru.anafro.quark.server.console.CommandLoop;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

/**
 * Thrown when command entered into command line is not found.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class NoSuchCommandException extends CommandException {

    /**
     * Creates a new exception instance.
     *
     * @param loop the command loop that received a wrong command.
     * @param commandName the command name that was not found.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public NoSuchCommandException(CommandLoop loop, String commandName) {
        super("There is no command with name " + quoted(commandName) + ". Did you mean %s?".formatted(quoted(loop.suggestCommand(commandName))));
    }
}
