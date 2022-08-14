package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;

public abstract class InstructionLexerState {
    protected final InstructionLexer lexer;
    protected boolean skipLexerIgnoredCharacters = false;
    private final InstructionLexerState previousState;

    public InstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        this.lexer = lexer;
        this.previousState = previousState;
    }

    public InstructionLexerState(InstructionLexer lexer) {
        this(lexer, null);
    }

    public abstract void handleCharacter(char currentCharacter);

    public void skipLexerIgnoredCharacters() {
        this.skipLexerIgnoredCharacters = true;
    }

    public void stopSkippingLexerIgnoredCharacters() {
        this.skipLexerIgnoredCharacters = false;
    }

    public boolean lexerIgnoredCharactersShouldBeSkipped() {
        return skipLexerIgnoredCharacters;
    }

    public InstructionLexerState getPreviousState() {
        return previousState;
    }

    public boolean hasPreviousState() {
        return previousState != null;
    }
}
