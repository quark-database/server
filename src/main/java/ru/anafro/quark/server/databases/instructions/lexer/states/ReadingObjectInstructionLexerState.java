package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.states.helpers.InstructionObjectRecognizer;

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
