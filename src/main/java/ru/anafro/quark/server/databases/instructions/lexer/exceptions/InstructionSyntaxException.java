package ru.anafro.quark.server.databases.instructions.lexer.exceptions;

import ru.anafro.quark.server.databases.instructions.lexer.states.InstructionLexerState;
import ru.anafro.quark.server.utils.strings.StringBuffer;

public class InstructionSyntaxException extends LexerException {
    private final InstructionLexerState state;
    private final String instruction, errorMessage, tipToFix;
    private final int errorBeginningCharacterIndex, errorLength;

    public InstructionSyntaxException(InstructionLexerState state, String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        super(formatInstructionSyntaxErrorMessage(state, instruction, errorMessage, tipToFix, errorBeginningCharacterIndex, errorLength));

        this.state = state;
        this.instruction = instruction;
        this.errorMessage = errorMessage;
        this.tipToFix = tipToFix;
        this.errorBeginningCharacterIndex = errorBeginningCharacterIndex;
        this.errorLength = errorLength;
    }

    private static String formatInstructionSyntaxErrorMessage(InstructionLexerState state, String instruction, String errorMessage, String tipToFix, int errorBeginningCharacterIndex, int errorLength) {
        if(errorLength <= 0) {
            throw new UnsupportedOperationException("Error length should be greater than 0");
        }

        StringBuffer formattedMessage = new StringBuffer();

        formattedMessage.appendLine("Instruction contains a syntax error: " + errorMessage + ".");
        formattedMessage.appendLine("Lexing state: " + state.getClass().getSimpleName());
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine(instruction);
        formattedMessage.append(" ".repeat(errorBeginningCharacterIndex));
        formattedMessage.append("~".repeat(errorLength));
        formattedMessage.nextLine();
        formattedMessage.appendLine("-".repeat(instruction.length()));
        formattedMessage.appendLine("TIP: " + tipToFix);

        System.out.println(formattedMessage.getValue());

        return formattedMessage.extractValue();
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
