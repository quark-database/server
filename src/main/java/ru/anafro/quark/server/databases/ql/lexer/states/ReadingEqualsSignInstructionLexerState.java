package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.EqualsInstructionToken;

public class ReadingEqualsSignInstructionLexerState extends InstructionLexerState {
    public ReadingEqualsSignInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == '=') {
            logger.debug("Found an equals sign. Expecting an object next time");
            lexer.pushToken(new EqualsInstructionToken());
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenInstructionParametersInstructionLexerState(lexer)));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Equals sign expected, but %s met".formatted(currentCharacter), "Add an equals sign between the instruction parameter and its value", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
