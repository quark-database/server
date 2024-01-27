package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.InstructionNameInstructionToken;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.validation.Validators;

import java.util.List;

public class ReadingInstructionHeaderInstructionLexerState extends InstructionLexerState {

    public ReadingInstructionHeaderInstructionLexerState(InstructionLexer lexer) {
        super(lexer);
        skipLexerIgnoredCharacters();
    }


    @Override
    public void handleCharacter(char currentCharacter) {
        // TODO: Rewrite this, because it's too hard to read. Split to multiple states if needed
        stopSkippingLexerIgnoredCharacters();

        if (Validators.validate(currentCharacter, Validators.IS_LATIN) || currentCharacter == '_') {
            logger.debug("Appending this character to the instruction name");
            lexer.pushCurrentCharacterToBuffer();
        } else if (lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            logger.debug("Appending one space to the instruction name. Next spaces will be ignored");
            lexer.getBuffer().append(' ');
        } else if (currentCharacter == ';' || currentCharacter == ':') {
            logger.debug(STR."Found \{currentCharacter}. Reading of instruction name is completed. Switching to 'between header and parameters' state");
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new BetweenHeaderAndParametersInstructionLexerState(lexer));
        } else if (!Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Found a non-latin character. Expecting that this is an object for a general parameter");
            lexer.pushToken(new InstructionNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingObjectInstructionLexerState(lexer, new BetweenHeaderAndParametersInstructionLexerState(lexer)));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), STR."Unexpected character \{currentCharacter}", "You have a typo somewhere around this character.", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        //
    }

    @Override
    public List<InstructionHint> makeHints() {
        return Quark.instructions()
                .asList()
                .stream()
                .filter(instruction -> instruction.getName().startsWith(lexer.getBufferContent()))
                .map(instruction -> InstructionHint.instruction(instruction.getName(), lexer.getBuffer().length()))
                .toList();
    }
}
