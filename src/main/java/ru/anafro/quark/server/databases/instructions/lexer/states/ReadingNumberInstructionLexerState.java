package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.FloatLiteralInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.IntegerLiteralInstructionToken;

public class ReadingNumberInstructionLexerState extends InstructionLexerState {
    public ReadingNumberInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(lexer.getBufferContent().isEmpty() && (currentCharacter == '-' || currentCharacter == '+')) {
            if(currentCharacter == '-') {
                lexer.pushCurrentCharacterToBuffer();
            }
        } else if(currentCharacter == '.') {
            if(lexer.getBufferContent().contains(".")) {
                throw new InstructionSyntaxException(this, lexer.getInstruction(), "An extra dot found in number", "Please, remove the additional dot(s) from the numbers", lexer.getCurrentCharacterIndex(), 1);
            } else {
                lexer.pushCurrentCharacterToBuffer();
            }
        } else if(Character.isDigit(currentCharacter)) {
            lexer.pushCurrentCharacterToBuffer();
        } else {
            String number = lexer.extractBufferContent();

            lexer.pushToken(number.contains(".") ? new FloatLiteralInstructionToken(number) : new IntegerLiteralInstructionToken(number));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.restoreState();
        }
    }
}
