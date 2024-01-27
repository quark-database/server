package ru.anafro.quark.server.database.data.parser.states;

import ru.anafro.quark.server.database.data.parser.RecordParser;

public abstract class RecordParserState {
    protected final RecordParser parser;
    private final RecordParserState previousState;

    public RecordParserState(RecordParser parser) {
        this(parser, null);
    }

    public RecordParserState(RecordParser parser, RecordParserState previousState) {
        this.parser = parser;
        this.previousState = previousState;
    }

    public RecordParserState getPreviousState() {
        return previousState;
    }

    public RecordParser getParser() {
        return parser;
    }

    public abstract void handleCharacter(char currentCharacter);
}
