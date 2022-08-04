package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;

public class SkippingWhitespacesInstructionLexerState extends InstructionLexerState {
    public SkippingWhitespacesInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        lexer.switchState(getPreviousState());
    }
}
