package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.EqualsInstructionToken;

public class ReadingEqualsSignInstructionLexerState extends InstructionLexerState {
    public ReadingEqualsSignInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == '=') {
            lexer.pushToken(new EqualsInstructionToken());
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenInstructionParametersInstructionLexerState(lexer)));
        }
    }
}
