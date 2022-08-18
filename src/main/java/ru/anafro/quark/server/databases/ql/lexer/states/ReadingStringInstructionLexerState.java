package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.StringLiteralInstructionToken;

public class ReadingStringInstructionLexerState extends InstructionLexerState {
    boolean inString = false;

    public ReadingStringInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if(currentCharacter == '"') {
            if(!inString) {
                logger.debug("Found the first quote, so it's going to be a string");
                inString = true;
            } else {
                logger.debug("Found the second quote, the string is ended. Restoring the state");
                lexer.pushToken(new StringLiteralInstructionToken(lexer.extractBufferContent()));
                lexer.restoreState();
            }
        } else if(inString) {
            logger.debug("Appending this character to a string");
            lexer.pushCurrentCharacterToBuffer();
        }
    }

    public boolean isInString() {
        return inString;
    }
}
