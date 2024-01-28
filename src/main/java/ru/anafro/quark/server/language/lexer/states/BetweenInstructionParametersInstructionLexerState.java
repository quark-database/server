package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.language.lexer.tokens.CommaInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class BetweenInstructionParametersInstructionLexerState extends InstructionLexerState {
    public BetweenInstructionParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (currentCharacter == ',') {
            logger.debug("Found ',', expecting an instruction parameter next time");
            lexer.pushToken(new CommaInstructionToken());
            lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
        } else if (currentCharacter == ';') {
            logger.debug("Found ';', the instruction is ended");
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected symbol after reading parameter value", "Did you write something extra after your instruction's parameter value? Remove everything between value and the comma or the semicolon", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The instruction is incomplete", "Complete the instruction", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
