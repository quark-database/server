package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.states.helpers.InstructionObjectRecognizer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ReadingInstructionHeaderInstructionLexerState extends InstructionLexerState {

    public ReadingInstructionHeaderInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }


    @Override
    public void handleCharacter(char currentCharacter) {
        // TODO: Rewrite this, because it's too hard to read. Split to multiple states if needed
        stopSkippingLexerIgnoredCharacters();

        if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Appending this character to the instruction name");
            lexer.pushCurrentCharacterToBuffer();
        } else if(lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            logger.debug("Appending one space to the instruction name. Next spaces will be ignored");
            lexer.getBuffer().append(' ');
        } else if(currentCharacter == ';' || currentCharacter == ':') {
            logger.debug("Found " + currentCharacter + ". Reading of instruction name is completed. Switching to 'between header and parameters' state");
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new BetweenHeaderAndParametersInstructionLexerState(lexer));
        } else if (!Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Found a non-latin character. Expecting that this is an object for a general parameter");
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new InstructionObjectRecognizer().recognizeObjectAndMakeLexerState(lexer, new BetweenHeaderAndParametersInstructionLexerState(lexer)));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Unexpected character " + currentCharacter, "You have a typo somewhere around this character.", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
