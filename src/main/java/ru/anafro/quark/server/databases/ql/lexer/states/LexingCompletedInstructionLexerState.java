package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.hints.InstructionHint;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.List;

public class LexingCompletedInstructionLexerState extends InstructionLexerState {
    public LexingCompletedInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        logger.debug("Instruction is completed with ';', but character " + currentCharacter + " is found. Oops");
        throw new InstructionSyntaxException(this, lexer.getInstruction(), "Instruction has finished with a semicolon, but '" + currentCharacter + "' was found", "Remove everything after the ';'", lexer.getCurrentCharacterIndex(), lexer.getInstruction().length() - lexer.getCurrentCharacterIndex());
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
