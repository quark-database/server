package ru.anafro.quark.server.console.parser;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.console.exceptions.ParsingFailedException;
import ru.anafro.quark.server.console.parser.states.CommandParserState;
import ru.anafro.quark.server.console.parser.states.ReadingCommandNameCommandParserState;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.strings.TextBuffer;
import ru.anafro.quark.server.utils.types.classes.Classes;

import static ru.anafro.quark.server.utils.collections.Lists.join;
import static ru.anafro.quark.server.utils.objects.Nulls.byDefault;

public class CommandParser {
    private final TextBuffer buffer = new TextBuffer();
    private final Logger logger = new Logger(this.getClass());
    private CommandParserState state = new ReadingCommandNameCommandParserState(this);
    private String command;
    private String commandName;
    private CommandArguments arguments = new CommandArguments();
    private int index = 0;

    public TextBuffer getBuffer() {
        return buffer;
    }

    public String getBufferContent() {
        return getBuffer().getContent();
    }

    public CommandParserState getState() {
        return state;
    }

    public void letTheNextStateStartFromCurrentCharacter() {
        index--;
    }

    public void switchState(CommandParserState newState) {
        this.state = newState;
    }

    public CommandArguments getArguments() {
        return arguments;
    }

    public int getIndex() {
        return index;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandString() {
        return command;
    }

    public Command parse(String command) {
        this.command = command;
        this.commandName = null;
        this.arguments = new CommandArguments();
        this.index = 0;
        this.state = new ReadingCommandNameCommandParserState(this);
        buffer.clear();

        if (command.isBlank()) {
            return null;
        }

        while (index != command.length()) {
            var currentCharacter = this.command.charAt(index);

            logger.debug(STR."""
                    -----------------------------------------------------------------------

                    \{command}
                    \{" ".repeat(index)}^

                    State:              \{Classes.getHumanReadableClassName(state)}
                    Command name:       \{byDefault(commandName, "(unset)")}
                    Arguments:          \{join(arguments.toList())}
                    Buffer content:     \{buffer.getContent()}
                    Current character:  \{currentCharacter}

                    -----------------------------------------------------------------------
                    """);

            state.handleCharacter(currentCharacter);
            index++;
        }

        if (buffer.isNotEmpty()) {
            state.whenParsingCompleteButBufferIsNotEmpty(buffer.getContent());
        }

        if (commandName == null) {
            throw new ParsingFailedException("Command parsing failed, because command name was not set");
        }

        var commands = Quark.commands();

        if (commands.doesntHave(commandName)) {
            throw new NoSuchCommandException(commandName);
        }

        Quark.commandLoop().forgetFailedCommand();
        return commands.get(commandName);
    }

    public Logger getLogger() {
        return logger;
    }
}
