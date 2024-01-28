package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.language.lexer.tokens.ClosingParenthesisInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.language.lexer.tokens.OpeningParenthesisInstructionToken;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.validation.Validators;

import java.util.List;

public class ReadingConstructorNameInstructionLexerState extends InstructionLexerState {
    boolean markerFound = false;

    public ReadingConstructorNameInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        // TODO: This is too hard to read. Splitting to multiple states can be a solution
        stopSkippingLexerIgnoredCharacters();

        if (currentCharacter != ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX && !isMarkerFound()) {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), STR."Constructor name should start with \{ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX} symbol", "Didn't you mean constructor, but something else? Or you just missed starting constructor symbol?", lexer.getCurrentCharacterIndex(), 1);
        }

        if (currentCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX) {
            logger.debug("Found '@', it's a constructor marker. Expecting the instruction name next time");
            cameAcrossWithMarker();
        } else if (lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            logger.debug("Appending one space to the constructor name. Next spaces will be ignored");
            lexer.getBuffer().append(' ');
        } else if (Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Appending this character to the constructor name");
            lexer.pushCurrentCharacterToBuffer();
        } else if (currentCharacter == '(') {
            logger.debug("Reading the constructor name is completed. Reading it's arguments");
            lexer.pushToken(new ConstructorNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.pushToken(new OpeningParenthesisInstructionToken());
            lexer.switchState(new ReadingNextConstructorArgumentInstructionLexerState(lexer, getPreviousState()));
        } else {
            logger.debug("Reading the constructor name is completed, but no arguments was provided");
            lexer.pushToken(new ConstructorNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.pushToken(new OpeningParenthesisInstructionToken());
            lexer.pushToken(new ClosingParenthesisInstructionToken());
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.restoreState();
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The constructor name was not called", "Call the constructor using parenthesis", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        var instruction = Quark.instruction(lexer.getTokens().stream().filter(token -> token.is("instruction name")).findFirst().orElseThrow().getValue());

        var currentParameter = lexer.getTokens()
                .stream()
                .filter(token -> token.is("parameter name"))
                .reduce((first, following) -> following);

        if (currentParameter.isPresent()) {
            var parameterType = instruction.getParameters().get(currentParameter.get().getValue()).getType();

            return Quark.constructors()
                    .asList()
                    .stream()
                    .filter(constructor -> parameterType.startsWith(constructor.getReturnDescription().getType().getName()))
                    .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent().replaceFirst(String.valueOf(ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX), "")))
                    .map(constructor -> InstructionHint.constructor(constructor.getName(), lexer.getBuffer().length()))
                    .toList();
        } else if (instruction.getParameters().hasGeneralParameter()) {
            return Quark.constructors()
                    .asList()
                    .stream()
                    .filter(constructor -> instruction.getParameters().getGeneralParameter().getType().startsWith(constructor.getReturnDescription().getType().getName()))
                    .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent().replaceFirst(String.valueOf(ConstructorNameInstructionToken.CONSTRUCTOR_PREFIX), "")))
                    .map(constructor -> InstructionHint.constructor(constructor.getName(), lexer.getBuffer().length()))
                    .toList();
        } else {
            return Lists.empty();
        }
    }

    public boolean isMarkerFound() {
        return markerFound;
    }

    // TODO: Rename this.
    public void cameAcrossWithMarker() {
        markerFound = true;
    }
}
