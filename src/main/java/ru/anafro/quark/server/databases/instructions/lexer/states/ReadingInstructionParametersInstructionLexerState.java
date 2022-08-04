package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ParameterNameInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ReadingInstructionParametersInstructionLexerState extends InstructionLexerState {
    public ReadingInstructionParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            lexer.pushCurrentCharacterToBuffer();
        } else if(currentCharacter == '=') {
            lexer.pushToken(new ParameterNameInstructionToken(lexer.extractBufferContent()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingEqualsSignInstructionLexerState(lexer));
        } else if(currentCharacter == ';') {
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected symbol '" + currentCharacter + "' in parameter name", "Did you make a typo while writing an instruction parameter?", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
