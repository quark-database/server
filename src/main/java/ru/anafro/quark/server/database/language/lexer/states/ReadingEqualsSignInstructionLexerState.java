package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.EqualsInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class ReadingEqualsSignInstructionLexerState extends InstructionLexerState {
    public ReadingEqualsSignInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (currentCharacter == '=') {
            logger.debug("Found an equals sign. Expecting an object next time");
            lexer.pushToken(new EqualsInstructionToken());
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenInstructionParametersInstructionLexerState(lexer)));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Equals sign expected, but %s met".formatted(currentCharacter), "Add an equals sign between the instruction parameter and its value", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        //
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
