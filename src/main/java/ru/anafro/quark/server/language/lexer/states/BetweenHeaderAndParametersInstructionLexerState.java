package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.language.lexer.tokens.ColonInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class BetweenHeaderAndParametersInstructionLexerState extends InstructionLexerState {
    public BetweenHeaderAndParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if (currentCharacter == ':') {
            logger.debug("Found ':', supposing that there's no general parameter. Switching to the state of reading instruction parameter");
            lexer.pushToken(new ColonInstructionToken());
            lexer.switchState(new ReadingInstructionParametersInstructionLexerState(lexer));
        } else if (currentCharacter == ';') {
            logger.debug("Found ';', supposing that instruction is ended");
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected character between instruction header and instruction's parameters", "Did you make a typo? Please, remove everything between the instruction name with general argument and instruction's additional parameters", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "An unexpected instruction end after reading the instruction name", "Put a semicolon or the list of the parameters after a colon", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Lists.empty();
    }
}
