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
        logger.debug("Expecting an object, so let's an instruction recognizer deal with it");
        stopSkippingLexerIgnoredCharacters();
        lexer.letTheNextStateStartFromCurrentCharacter();
        lexer.switchState(new InstructionObjectRecognizer().recognizeObjectAndMakeLexerState(lexer, getPreviousState()));
    }
}
