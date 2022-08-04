package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ColonInstructionToken;

public class ReadingColonInstructionLexerState extends InstructionLexerState {
    public ReadingColonInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if(currentCharacter == ':') {
            lexer.pushToken(new ColonInstructionToken());
            lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected symbol '" + currentCharacter + "' instead of instruction parameter starting semicolon", "Did you add something extra between instruction name and its parameters?", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
