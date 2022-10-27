package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.databases.ql.parser.InstructionParser;

import java.util.ArrayList;

public class BeforeInstructionParsing extends Event {
    private final String originalInstruction;
    private final ArrayList<InstructionToken> tokens;
    private final InstructionParser parser;

    public BeforeInstructionParsing(String originalInstruction, ArrayList<InstructionToken> tokens, InstructionParser parser) {
        this.originalInstruction = originalInstruction;
        this.tokens = tokens;
        this.parser = parser;
    }

    public String getOriginalInstruction() {
        return originalInstruction;
    }

    public ArrayList<InstructionToken> getTokens() {
        return tokens;
    }

    public InstructionParser getParser() {
        return parser;
    }
}
