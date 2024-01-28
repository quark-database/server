package ru.anafro.quark.server.language.exceptions;

import ru.anafro.quark.server.language.lexer.exceptions.LexerException;
import ru.anafro.quark.server.utils.integers.Integers;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public class InstructionSyntaxException extends LexerException {

    public InstructionSyntaxException(Object state, String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        super(formatInstructionSyntaxErrorMessage(instruction, errorMessage, tipToFix, errorBeginningCharacterIndex, errorLength));

    }

    public InstructionSyntaxException(Object state, String errorMessage, String tipToFix) {
        this(state, "", errorMessage, tipToFix, 0, 1); // TODO: Instruction should be set. Replace "" with instruction somehow.
    }

    private static String formatInstructionSyntaxErrorMessage(String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        if (errorLength <= 0) {
            throw new UnsupportedOperationException("Error length should be greater than 0");
        }

        TextBuffer formattedMessage = new TextBuffer();

        formattedMessage.appendLine(errorMessage);
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine(instruction);
        formattedMessage.append(" ".repeat(Integers.limit(errorBeginningCharacterIndex, 0, Integer.MAX_VALUE)));
        formattedMessage.append("~".repeat(errorLength));
        formattedMessage.nextLine();
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine(STR."TIP: \{tipToFix}");

        return formattedMessage.extractContent();
    }
}
