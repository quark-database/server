package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.ClosingParenthesisInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class ReadingNextConstructorArgumentInstructionLexerState extends InstructionLexerState {
    public ReadingNextConstructorArgumentInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (currentCharacter == ')') {
            logger.debug("Closing parenthesis is found. Restoring the state");
            lexer.pushToken(new ClosingParenthesisInstructionToken());
            lexer.restoreState();
        } else {
            logger.debug("Found something other that an opening parenthesis. Let a 'reading object' state deal with it");
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenConstructorArgumentsInstructionLexerState(lexer, this)));
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "An unexpected instruction end after reading constructor arguments", "Close the argument list with a closing parenthesis", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
