package ru.anafro.quark.server.plugins.events;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;

public class BeforeInstructionLexing extends Event {
    private final String originalInstruction;
    private final InstructionLexer lexer;

    public BeforeInstructionLexing(String originalInstruction, InstructionLexer lexer) {
        this.originalInstruction = originalInstruction;
        this.lexer = lexer;
    }

    public String getOriginalInstruciton() {
        return originalInstruction;
    }

    public InstructionLexer getLexer() {
        return lexer;
    }
}
