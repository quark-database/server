package ru.anafro.quark.server.databases.ql.parser.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.strings.English;
import ru.anafro.quark.server.utils.strings.Strings;

public abstract class InstructionParserState {
    protected final InstructionParser parser;
    protected final InstructionParserState previousState;
    protected final Logger logger;

    public InstructionParserState(InstructionParser parser, InstructionParserState previousState) {
        this.parser = parser;
        this.logger = parser.getLogger();
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

    protected void throwExecutionError(String expected, String met) {
        throw new InstructionSyntaxException(this, Strings.capitalize("%s %s expected, but %s %s met".formatted(English.articleFor(expected), expected, English.articleFor(met), met)), "You probably missed something, or need to remove something, or put something in a bad order");
    }
}
