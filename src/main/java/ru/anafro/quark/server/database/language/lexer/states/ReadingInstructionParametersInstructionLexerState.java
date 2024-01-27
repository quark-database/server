package ru.anafro.quark.server.database.language.lexer.states;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.database.language.hints.InstructionHint;
import ru.anafro.quark.server.database.language.lexer.InstructionLexer;
import ru.anafro.quark.server.database.language.lexer.tokens.ParameterNameInstructionToken;
import ru.anafro.quark.server.database.language.lexer.tokens.SemicolonInstructionToken;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.validation.Validators;

import java.util.List;

public class ReadingInstructionParametersInstructionLexerState extends InstructionLexerState {
    public ReadingInstructionParametersInstructionLexerState(InstructionLexer lexer) {
        super(lexer, null);
        skipLexerIgnoredCharacters();
    }

    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if (Validators.validate(currentCharacter, Validators.IS_LATIN)) {
            logger.debug("Appending this character to the parameter name");
            lexer.pushCurrentCharacterToBuffer();
        } else if (lexer.currentCharacterShouldBeIgnored() && !lexer.getBufferContent().endsWith(" ")) {
            logger.debug("Appending one space to the parameter name. Next spaces will be ignored");
            lexer.getBuffer().append(' ');
        } else if (currentCharacter == '=') {
            logger.debug("Found an equals sign. Let the separate state deal with it. And completing the parameter name reading");
            lexer.pushToken(new ParameterNameInstructionToken(lexer.extractBufferContent().strip()));
            lexer.letTheNextStateStartFromCurrentCharacter();
            lexer.switchState(new ReadingEqualsSignInstructionLexerState(lexer));
        } else if (currentCharacter == ';') {
            logger.debug("Semicolon found. Instruction is completed");
            lexer.pushToken(new SemicolonInstructionToken());
            lexer.switchState(new LexingCompletedInstructionLexerState(lexer));
        } else {
            throw new InstructionSyntaxException(this, lexer.getInstruction(), STR."Unexpected symbol '\{currentCharacter}' in parameter name", "Did you make a typo while writing an instruction parameter?", lexer.getCurrentCharacterIndex(), 1);
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The parameter was not set", "Set the parameter with an equals sign and a value", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        var instruction = Quark.instructions().get(lexer.getTokens().stream().filter(token -> token.is("instruction name")).findFirst().orElseThrow().getValue());

        return instruction
                .getParameters()
                .getAdditionalParameters()
                .stream()
                .filter(parameter -> lexer  // filter if instruction parameter is not in the lexer read ones.
                        .getTokens()
                        .stream()
                        .filter(token -> token.is("parameter name"))
                        .noneMatch(parameterToken -> parameter.getName().equals(parameterToken.getValue())))
                .filter(parameter -> parameter.getName().startsWith(lexer.getBufferContent()))
                .map(parameter -> InstructionHint.parameter(instruction.getName(), parameter.getName(), lexer.getBuffer().length()))
                .toList();
    }
}
