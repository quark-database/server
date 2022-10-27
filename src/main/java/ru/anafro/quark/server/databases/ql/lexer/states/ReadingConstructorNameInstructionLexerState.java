package ru.anafro.quark.server.databases.ql.lexer.states;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.hints.InstructionHint;
import ru.anafro.quark.server.databases.ql.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.ql.lexer.tokens.ConstructorNameInstructionToken;
import ru.anafro.quark.server.databases.ql.lexer.tokens.OpeningParenthesisInstructionToken;
import ru.anafro.quark.server.utils.containers.Lists;
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

        if(currentCharacter != ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER && !isMarkerFound()) {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), "Constructor name should start with " + ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER + " symbol", "Didn't you mean constructor, but something else? Or you just missed starting constructor symbol?", lexer.getCurrentCharacterIndex(), 1);
        }

        if(currentCharacter == ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER) {
            logger.debug("Found '@', it's a constructor marker. Expecting the instruction name next time");
            cameAcrossWithMarker();
        } else if(lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            logger.debug("Appending one space to the constructor name. Next spaces will be ignored");
            lexer.getBuffer().append(' ');
        } else if(Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Appending this character to the constructor name");
            lexer.pushCurrentCharacterToBuffer();
        } else if(currentCharacter == '(') {
            logger.debug("Reading the constructor name is completed. Reading it's arguments");
            lexer.pushToken(new ConstructorNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.pushToken(new OpeningParenthesisInstructionToken());
            lexer.switchState(new ReadingNextConstructorArgumentInstructionLexerState(lexer, getPreviousState()));
        }
    }

    @Override
    public List<InstructionHint> makeHints() {
        var instruction = Quark.instructions().get(lexer.getTokens().stream().filter(token -> token.is("instruction name")).findFirst().get().getValue());

        var currentParameter = lexer.getTokens()
                .stream()
                .filter(token -> token.is("parameter name"))
                .reduce((first, following) -> following);

        if(currentParameter.isPresent()) {
            var parameterType = instruction.getParameters().get(currentParameter.get().getValue()).getType();

            return Quark.constructors()
                    .asList()
                    .stream()
                    .filter(constructor -> parameterType.startsWith(constructor.getReturnDescription().getType().getName()))
                    .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent().replaceFirst(String.valueOf(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER), "")))
                    .map(constructor -> InstructionHint.constructor(constructor.getName(), lexer.getBuffer().length()))
                    .toList();
        } else if(instruction.getParameters().hasGeneralParameter()) {
            return Quark.constructors()
                    .asList()
                    .stream()
                    .filter(constructor -> instruction.getParameters().getGeneralParameter().getType().startsWith(constructor.getReturnDescription().getType().getName()))
                    .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent().replaceFirst(String.valueOf(ConstructorNameInstructionToken.CONSTRUCTOR_NAME_MARKER), "")))
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
