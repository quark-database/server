package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ParameterNameInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ReadingInstructionParametersInstructionLexerState extends InstructionLexerState {
    public ReadingInstructionParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Appending this character to the parameter name");
            lexer.pushCurrentCharacterToBuffer();
        } else if(currentCharacter == '=') {
            logger.debug("Found an equals sign. Let the separate state deal with it. And completing the parameter name reading");
            lexer.pushToken(new ParameterNameInstructionToken(lexer.extractBufferContent()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingEqualsSignInstructionLexerState(lexer));
        } else if(currentCharacter == ';') {
            logger.debug("Semicolon found. Instruction is completed");
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected symbol '" + currentCharacter + "' in parameter name", "Did you make a typo while writing an instruction parameter?", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
