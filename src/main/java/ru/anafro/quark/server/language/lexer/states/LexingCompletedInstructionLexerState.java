package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class LexingCompletedInstructionLexerState extends InstructionLexerState {
    public LexingCompletedInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        logger.debug(STR."Instruction is completed with ';', but character \{currentCharacter} is found. Oops");
        throw new InstructionSyntaxException(this, lexer.getInstruction(), STR."Instruction has finished with a semicolon, but '\{currentCharacter}' was found", "Remove everything after the ';'", lexer.getCurrentCharacterIndex(), lexer.getInstruction().length() - lexer.getCurrentCharacterIndex());
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
