package ru.anafro.quark.server.databases.ql.lexer.states.helpers;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.states.InstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.states.ReadingConstructorNameInstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.states.ReadingNumberInstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.states.ReadingStringInstructionLexerState;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.StringLiteralInstructionToken;
import ru.anafro.quark.server.utils.arrays.Arrays;

public class InstructionObjectRecognizer {
    public InstructionLexerState recognizeObjectAndMakeLexerState(InstructionLexer lexer, InstructionLexerState previousState, char firstCharacter) {
        if(firstCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER) {
            return new ReadingConstructorNameInstructionLexerState(lexer, previousState);
        }

        if(firstCharacter == StringLiteralInstructionToken.STRING_LITERAL_QUOTE) {
            return new ReadingStringInstructionLexerState(lexer, previousState);
        }

        if(Character.isDigit(firstCharacter) || Arrays.contains(new Character[] {'+', '-'}, firstCharacter)) {
            return new ReadingNumberInstructionLexerState(lexer, previousState);
        }

        throw new InstructionSyntaxException(previousState, lexer.getInstruction(), "Object expected, but none of the values can be started with '" + firstCharacter + "'", "Did you make a typo? Or missed '@' before constructor name? E.g. @array(). Note that constants must also start with that symbol.", lexer.getCurrentCharacterIndex(), 1);
    }
}
