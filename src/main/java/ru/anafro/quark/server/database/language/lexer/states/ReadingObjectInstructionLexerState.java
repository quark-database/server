package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.database.language.lexer.tokens.StringLiteralInstructionToken;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

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

        if (currentCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX) {
            lexer.switchState(new ReadingConstructorNameInstructionLexerState(lexer, getPreviousState()));
        } else if (currentCharacter == StringLiteralInstructionToken.STRING_LITERAL_QUOTE) {
            lexer.switchState(new ReadingStringInstructionLexerState(lexer, getPreviousState()));
        } else if (Character.isDigit(currentCharacter) || Arrays.contains(new Character[]{'+', '-'}, currentCharacter)) {
            lexer.switchState(new ReadingNumberInstructionLexerState(lexer, getPreviousState()));
        } else {
            // TODO: Replace '@' with an existing constant
            throw new InstructionSyntaxException(getPreviousState(), lexer.getInstruction(), STR."Object expected, but none of the values can be started with '\{currentCharacter}'", "Did you make a typo? Or missed '@' before constructor name? E.g. @list(). Note that constants must also start with that symbol.", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The object is incomplete", "Complete the object", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        var instructionName = lexer.getTokens().stream().filter(token -> token.is("instruction name")).findFirst().orElseThrow().getValue();

        if (Quark.instructions().doesntHave(instructionName)) {
            return Lists.empty();
        }

        var instruction = Quark.instructions().get(instructionName);
        var tokens = lexer.getTokens();

        var type = instruction.getParameters().get(tokens.stream().filter(token -> token.is("parameter name")).findFirst().orElseThrow().getValue()).getType();

        return Quark.constructors()
                .asList()
                .stream()
                .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent().replaceFirst(String.valueOf(ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX), "")))
                .filter(constructor -> type.startsWith(constructor.getReturnDescription().getType().getName()))
                .map(constructor -> InstructionHint.constructor(constructor.getName(), lexer.getBuffer().length()))
                .toList();
    }
}
