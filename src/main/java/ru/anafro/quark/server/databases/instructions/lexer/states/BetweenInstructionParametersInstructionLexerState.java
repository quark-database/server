package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ColonInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.CommaInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.SemicolonInstructionToken;

public class BetweenInstructionParametersInstructionLexerState extends InstructionLexerState {
    public BetweenInstructionParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == ',') {
            lexer.pushToken(new CommaInstructionToken());
            lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
        } else if(currentCharacter == ';') {
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected symbol after reading parameter value", "Did you write something extra after your instruction's parameter value? Remove everything between value and the comma or the semicolon", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
