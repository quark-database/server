package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.CommaInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class BetweenConstructorArgumentsInstructionLexerState extends InstructionLexerState {
    public BetweenConstructorArgumentsInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (currentCharacter == ',') {
            logger.debug("Found a comma between two constructor arguments. Restoring the state");
            lexer.pushToken(new CommaInstructionToken());
            lexer.restoreState();
        } else if (currentCharacter == ')') {
            logger.debug("Found a closing parenthesis. Supposing that constructor argument list is ended. Restoring the state");
            lexer.letTheNextStateStartFromCurrentCharacter(); // TODO: ?
            lexer.restoreState();
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), STR."Unexpected character '\{currentCharacter}' between constructor arguments", "Did you put extra comma or forgot one? Or just typed an extra letter after argument?", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The constructor call is incomplete", "Put a parenthesis", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
