package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.lexer.tokens.InstructionToken;
import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;

import java.util.stream.Collectors;

public class InstructionLexerDebugFrame extends DebugFrame {
    private TextArea lexerOutputArea;
    private TextField instructionInputField;



    public InstructionLexerDebugFrame() {
        super("Instruction Lexer", 600, 400);
    }

    private void updateLexerOutput() {
        try {
            lexerOutputArea.setText(server.getInstructionLexer().lex(instructionInputField.getText()).stream().map(InstructionToken::toString).collect(Collectors.joining("\n")));
        } catch(InstructionSyntaxException exception) {
            lexerOutputArea.setText(exception.getMessage());
        }
    }

    @Override
    protected void constructInterface() {
        lexerOutputArea = TextArea.console(0, 0, 600, 380);
        lexerOutputArea.setEditable(false);
        lexerOutputArea.setLineWrap(true);

        instructionInputField = TextField.console(0, 380, 600, 20, this::updateLexerOutput);

        add(lexerOutputArea);
        add(instructionInputField);
    }
}
