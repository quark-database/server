package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;

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
