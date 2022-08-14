package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.states.helpers.InstructionObjectRecognizer;

public class ReadingObjectInstructionLexerState extends InstructionLexerState {
    public ReadingObjectInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();
        lexer.letTheNextStateStartFromCurrentCharacter();
        lexer.switchState(new InstructionObjectRecognizer().recognizeObjectAndMakeLexerState(lexer, getPreviousState(), currentCharacter));
    }
}
