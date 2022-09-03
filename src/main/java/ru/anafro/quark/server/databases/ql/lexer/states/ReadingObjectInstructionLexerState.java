package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.StringLiteralInstructionToken;
import ru.anafro.quark.server.utils.arrays.Arrays;

public class ReadingObjectInstructionLexerState extends InstructionLexerState {
    public ReadingObjectInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        logger.debug("Expecting an object, trying to recognise an object type");
        stopSkippingLexerIgnoredCharacters();

        lexer.letTheNextStateStartFromCurrentCharacter();

        if(currentCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER) {
            lexer.switchState(new ReadingConstructorNameInstructionLexerState(lexer, getPreviousState()));
        } else if(currentCharacter == StringLiteralInstructionToken.STRING_LITERAL_QUOTE) {
            lexer.switchState(new ReadingStringInstructionLexerState(lexer, getPreviousState()));
        } else if(Character.isDigit(currentCharacter) || Arrays.contains(new Character[] {'+', '-'}, currentCharacter)) {
            lexer.switchState(new ReadingNumberInstructionLexerState(lexer, getPreviousState()));
        } else {
            // TODO: Replace '@' with an existing constant
            throw new InstructionSyntaxException(getPreviousState(), lexer.getInstruction(), "Object expected, but none of the values can be started with '" + currentCharacter + "'", "Did you make a typo? Or missed '@' before constructor name? E.g. @list(). Note that constants must also start with that symbol.", lexer.getCurrentCharacterIndex(), 1);
        }
    }
}
