package ru.anafro.quark.server.console.parser;

import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.exceptions.ParsingFailedException;
import ru.anafro.quark.server.console.parser.states.CommandParserState;
import ru.anafro.quark.server.console.parser.states.ReadingCommandNameCommandParserState;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.StringBuffer;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class CommandParser {
    private CommandParserState state = new ReadingCommandNameCommandParserState(this);
    private String command;
    private String commandName;
    private CommandArguments arguments = new CommandArguments();
    private final StringBuffer buffer = new StringBuffer();
    private final Logger logger = new Logger(this.getClass());
    private int index = 0;

    public StringBuffer getBuffer() {
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

    public void setCommandName(String commandName) {
        this.commandName = commandName;
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

    public String getCommandString() {
        return command;
    }

    public void parse(String command) {
        this.command = command;
        this.commandName = null;
        this.arguments = new CommandArguments();
        this.index = 0;
        this.state = new ReadingCommandNameCommandParserState(this);
        buffer.clear();

        if(command.isBlank()) {
            return;
        }

        while(index != command.length()) {
            logger.debug("State: " + state.getClass().getSimpleName());
            logger.debug("Command name: " + (commandName == null ? "<was not set>" : commandName));
            logger.debug("Arguments: " + Lists.join(arguments.toList()));
            logger.debug("Buffer: " + quoted(getBufferContent()));
            logger.debug("Character: " + quoted(String.valueOf(command.charAt(index))));
            logger.debug(command);
            logger.debug(" ".repeat(index) + "^");
            logger.debug("-".repeat(50));

            state.handleCharacter(this.command.charAt(index));
            index++;
        }

        if(!buffer.isEmpty()) {
            state.whenParsingCompleteButBufferIsNotEmpty(buffer.getContent());
        }

        if(commandName == null) {
            throw new ParsingFailedException("Command parsing failed, because command name was not set");
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public String getCommand() {
        return command;
    }
}
