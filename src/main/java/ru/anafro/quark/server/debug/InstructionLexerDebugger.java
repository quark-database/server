package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.database.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;

public class InstructionLexerDebugger extends Debugger {
    private final TextArea lexerOutputArea;
    private final TextField instructionInputField;

    public InstructionLexerDebugger() {
        super("Instruction Lexer", "lexer", 600, 400);

        lexerOutputArea = TextArea.console(0, 0, 600, 380);
        lexerOutputArea.setEditable(false);
        lexerOutputArea.setLineWrap(true);

        instructionInputField = TextField.console(0, 380, 600, 20, this::updateLexerOutput);

        add(lexerOutputArea);
        add(instructionInputField);
    }

    private void updateLexerOutput() {
        var lexer = Quark.server().getLexer();

        try {
            var tokens = lexer.lex(instructionInputField.getText());
            lexerOutputArea.setText(Lists.join(tokens, "\n"));
        } catch (InstructionSyntaxException exception) {
            lexerOutputArea.setText(exception.getMessage());
        }
    }
}
