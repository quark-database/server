package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.logging.Logger;

import java.util.List;

public abstract class InstructionLexerState {
    protected final InstructionLexer lexer;
    protected final Logger logger;
    private final InstructionLexerState previousState;
    protected boolean skipLexerIgnoredCharacters = false;

    public InstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        this.lexer = lexer;
        this.logger = lexer.getLogger();
        this.previousState = previousState;
    }

    public InstructionLexerState(InstructionLexer lexer) {
        this(lexer, null);
    }

    public abstract void handleCharacter(char currentCharacter);

    public abstract void handleBufferTrash();

    public abstract List<InstructionHint> makeHints();

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
