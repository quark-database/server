package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.CommaInstructionToken;

public class BetweenConstructorArgumentsInstructionLexerState extends InstructionLexerState {
    public BetweenConstructorArgumentsInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == ',') {
            lexer.pushToken(new CommaInstructionToken());
            lexer.restoreState();
        } else if(currentCharacter == ')') {
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.restoreState();
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected character '" + currentCharacter + "' between constructor arguments", "Did you put extra comma or forgot one? Or just typed an extra letter after argument?", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
