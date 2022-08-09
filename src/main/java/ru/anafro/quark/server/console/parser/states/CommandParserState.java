package ru.anafro.quark.server.console.parser.states;

import ru.anafro.quark.server.console.parser.CommandParser;

public abstract class CommandParserState {
    protected final CommandParser parser;

    public CommandParserState(CommandParser parser) {
        this.parser = parser;
    }

    public abstract void handleCharacter(char currentCharacter);
    public abstract void whenParsingCompleteButBufferIsNotEmpty(String bufferContent);

    public CommandParser getParser() {
        return parser;
    }
}
