package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ClosingParenthesisInstructionToken;

public class ReadingNextConstructorArgumentInstructionLexerState extends InstructionLexerState {
    public ReadingNextConstructorArgumentInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == ')') {
            logger.debug("Closing parenthesis is found. Restoring the state");
            lexer.pushToken(new ClosingParenthesisInstructionToken());
            lexer.restoreState();
        } else {
            logger.debug("Found something other that an opening parenthesis. Let a 'reading object' state deal with it");
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenConstructorArgumentsInstructionLexerState(lexer, this)));
        }
    }
}
