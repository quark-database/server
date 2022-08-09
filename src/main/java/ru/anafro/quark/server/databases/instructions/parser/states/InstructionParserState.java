package ru.anafro.quark.server.databases.instructions.parser.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.databases.instructions.parser.InstructionParser;

public abstract class InstructionParserState {
    private final InstructionParser parser;
    private final InstructionParserState previousState;

    public InstructionParserState(InstructionParser parser, InstructionParserState previousState) {
        this.parser = parser;
        this.previousState = previousState;
    }

    public InstructionParserState(InstructionParser parser) {
        this(parser, null);
    }

    public abstract void handleToken(InstructionToken token);

    public InstructionParser getParser() {
        return parser;
    }

    public boolean hasPreviousState() {
        return getPreviousState() != null;
    }

    public InstructionParserState getPreviousState() {
        return previousState;
    }
}
