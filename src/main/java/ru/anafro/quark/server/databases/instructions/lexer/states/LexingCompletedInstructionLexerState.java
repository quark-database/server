package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;

public class LexingCompletedInstructionLexerState extends InstructionLexerState {
    public LexingCompletedInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        throw new InstructionSyntaxException(this, lexer.getInstruction(), "Instruction has finished with a semicolon, but '" + currentCharacter + "' was found", "Remove everything after the ';'", lexer.getCurrentCharacterIndex(), lexer.getInstruction().length() - lexer.getCurrentCharacterIndex());
    }
}
