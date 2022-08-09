package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ColonInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.SemicolonInstructionToken;

public class BetweenHeaderAndParametersInstructionLexerState extends InstructionLexerState {
    public BetweenHeaderAndParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == ':') {
            lexer.pushToken(new ColonInstructionToken());
            lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
        } else if(currentCharacter == ';') {
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected character between instruction header and instruction's parameters", "Did you make a typo? Please, remove everything between the instruction name with general argument and instruction's additional parameters", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
