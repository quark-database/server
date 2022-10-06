package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.FloatLiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.IntegerLiteralInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.LongLiteralInstructionToken;

public class ReadingNumberInstructionLexerState extends InstructionLexerState {
    public ReadingNumberInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(lexer.getBufferContent().isEmpty() && (currentCharacter == '-' || currentCharacter == '+')) {
            if(currentCharacter == '-') {
                logger.debug("Found a minus sign while buffer is empty. Appending it to the buffer");
                lexer.pushCurrentCharacterToBuffer();
            }
        } else if(currentCharacter == '.') {
            if(lexer.getBufferContent().contains(".")) {
                throw new InstructionSyntaxException(this, lexer.getInstruction(), "An extra dot found in number", "Please, remove the additional dot(s) from the numbers", lexer.getCurrentCharacterIndex(), 1);
            } else {
                logger.debug("Found a dot & there's no dots inside the buffer");
                lexer.pushCurrentCharacterToBuffer();
            }
        } else if(Character.isDigit(currentCharacter)) {
            logger.debug("Found a digit, appending it to the number");
            lexer.pushCurrentCharacterToBuffer();
        } else if(currentCharacter == 'L') {
            logger.debug("Found an 'L' character. We consider read token as a long.");
            lexer.pushToken(new LongLiteralInstructionToken(lexer.extractBufferContent()));
            lexer.restoreState();
        } else {
            logger.debug("Extracting the number from the buffer, because we found something that should not be inside a number. Restoring the state");

            String number = lexer.extractBufferContent();

            lexer.pushToken(number.contains(".") ? new FloatLiteralInstructionToken(number) : new IntegerLiteralInstructionToken(number));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.restoreState();
        }
    }
}
