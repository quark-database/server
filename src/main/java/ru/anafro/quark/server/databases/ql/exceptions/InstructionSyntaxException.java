package ru.anafro.quark.server.databases.ql.exceptions;

import ru.anafro.quark.server.databases.ql.lexer.exceptions.LexerException;
import ru.anafro.quark.server.utils.strings.TextBuffer;

public class InstructionSyntaxException extends LexerException {
    private final String instruction, errorMessage, tipToFix;
    private final int errorBeginningCharacterIndex, errorLength;

    public InstructionSyntaxException(Object state, String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        super(formatInstructionSyntaxErrorMessage(state.getClass(), instruction, errorMessage, tipToFix, errorBeginningCharacterIndex, errorLength));

        this.instruction = instruction;
        this.errorMessage = errorMessage;
        this.tipToFix = tipToFix;
        this.errorBeginningCharacterIndex = errorBeginningCharacterIndex;
        this.errorLength = errorLength;
    }

    public InstructionSyntaxException(Object state, String errorMessage, String tipToFix) {
        this(state, "", errorMessage, tipToFix, 0, 1); // TODO: Instruction should be set. Replace "" with instruction somehow.
    }

    private static String formatInstructionSyntaxErrorMessage(Class<?> stateClass, String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        if(errorLength <= 0) {
            throw new UnsupportedOperationException("Error length should be greater than 0");
        }

        TextBuffer formattedMessage = new TextBuffer();

        formattedMessage.appendLine("Instruction contains a syntax error: " + errorMessage + ".");
        formattedMessage.appendLine("Lexing state: " + stateClass.getSimpleName()); // TODO: Split class name by words with spaces
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine(instruction);
        formattedMessage.append(" ".repeat(errorBeginningCharacterIndex));
        formattedMessage.append("~".repeat(errorLength));
        formattedMessage.nextLine();
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine("TIP: " + tipToFix);

        System.out.println(formattedMessage.getContent());

        return formattedMessage.extractContent();
    }

    public String getInstruction() {
        return instruction;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTipToFix() {
        return tipToFix;
    }

    public int getErrorBeginningCharacterIndex() {
        return errorBeginningCharacterIndex;
    }

    public int getErrorLength() {
        return errorLength;
    }
}
