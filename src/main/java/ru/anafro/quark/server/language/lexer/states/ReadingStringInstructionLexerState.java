package ru.anafro.quark.server.language.lexer.states;

import ru.anafro.quark.server.database.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.hints.InstructionHint;
import ru.anafro.quark.server.language.lexer.InstructionLexer;
import ru.anafro.quark.server.language.lexer.tokens.StringLiteralInstructionToken;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.arrays.Arrays;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class ReadingStringInstructionLexerState extends InstructionLexerState {
    private static final char ESCAPE_CHAR = '\\';
    private static final char QUOTATION_MARK = '"';
    boolean inString = false;
    boolean escapeMode = false;

    public ReadingStringInstructionLexerState(InstructionLexer lexer, InstructionLexerState previousState) {
        super(lexer, previousState);
        skipLexerIgnoredCharacters();
    }

    // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
    @Override
    public void handleCharacter(char currentCharacter) {
        stopSkippingLexerIgnoredCharacters();

        if (escapeMode) {
            lexer.getBuffer().append(new RecordCharacterEscapeService().escaped(currentCharacter));
            escapeMode = false;
        } else if (currentCharacter == ESCAPE_CHAR) {
            escapeMode = true;
        } else if (currentCharacter == QUOTATION_MARK) {
            if (inString) {
                logger.debug("Found the second quote, the string is ended. Restoring the state");
                lexer.pushToken(new StringLiteralInstructionToken(lexer.extractBufferContent()));
                lexer.restoreState();
            } else {
                logger.debug("Found the first quote, so it's going to be a string");
                inString = true;
            }
        } else if (inString) {
            logger.debug("Appending this character to a string");
            lexer.pushCurrentCharacterToBuffer();
        }
    }

    @Override
    public void handleBufferTrash() {
        var instruction = lexer.getInstruction();
        throw new InstructionSyntaxException(this, instruction, "The string is not closed", STR."Close the string with \{QUOTATION_MARK}", instruction.length() - 1, 1);
    }

    @Override
    public List<InstructionHint> makeHints() {
        if (inString) {
            return Lists.empty();
        }

        return Quark.constructors()
                .asList()
                .stream()
                .filter(constructor -> constructor.getName().startsWith(lexer.getBufferContent()))
                .filter(constructor -> Arrays.contains(Arrays.array("str"), constructor.getReturnDescription().getType().getName()))
                .map(constructor -> InstructionHint.constructor(constructor.getName(), lexer.getBuffer().length()))
                .toList();
    }

    public boolean isInString() {
        return inString;
    }
}
