package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.StringLiteralInstructionToken;

public class ReadingStringInstructionLexerState extends InstructionLexerState {
    boolean inString = false;

    public ReadingStringInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if(currentCharacter == '"') {
            if(!inString) {
                inString = true;
            } else {
                lexer.pushToken(new StringLiteralInstructionToken(lexer.extractBufferContent()));
                lexer.restoreState();
            }
        } else if(inString) {
            lexer.pushCurrentCharacterToBuffer();
        }
    }

    public boolean isInString() {
        return inString;
    }
}
