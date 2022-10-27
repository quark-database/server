package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.hints.InstructionHint;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.CommaInstructionToken;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.List;

public class BetweenConstructorArgumentsInstructionLexerState extends InstructionLexerState {
    public BetweenConstructorArgumentsInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter == ',') {
            logger.debug("Found a comma between two constructor arguments. Restoring the state");
            lexer.pushToken(new CommaInstructionToken());
            lexer.restoreState();
        } else if(currentCharacter == ')') {
            logger.debug("Found a closing parenthesis. Supposing that constructor argument list is ended. Restoring the state");
            lexer.letTheNextStateStartFromCurrentCharacter(); // TODO: ?
            lexer.restoreState();
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected character '" + currentCharacter + "' between constructor arguments", "Did you put extra comma or forgot one? Or just typed an extra letter after argument?", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
