package ru.anafro.quark.server.databases.instructions.lexer.states;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.tokens.OpeningParenthesisInstructionToken;
import ru.anafro.quark.server.utils.validation.Validators;

public class ReadingConstructorNameInstructionLexerState extends InstructionLexerState {
    boolean markerFound = false;
    public ReadingConstructorNameInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        if(currentCharacter != ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER && !isMarkerFound()) {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Constructor name should start with " + ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER + " symbol", "Didn't you mean constructor, but something else? Or you just missed starting constructor symbol?", lexer.getCurrentCharacterIndex(), 1);
        }

        if(currentCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER) {
            cameAcrossWithMarker();
        } else if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            lexer.pushCurrentCharacterToBuffer();
        } else if(currentCharacter == '(') {
            lexer.pushToken(new ConstructorNameInstructionToken(lexer.extractBufferContent()));
            lexer.pushToken(new OpeningParenthesisInstructionToken());
            lexer.switchState(new ReadingNextConstructorArgumentInstructionLexerState(lexer, getPreviousState()));
        }
    }

    public boolean isMarkerFound() {
        return markerFound;
    }

    public void cameAcrossWithMarker() {
        markerFound = true;
    }
}
