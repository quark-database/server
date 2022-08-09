package ru.anafro.quark.server.debug.ui;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.debug.ui.components.DebugFrame;
import ru.anafro.quark.server.debug.ui.components.Panel;
import ru.anafro.quark.server.debug.ui.components.TextArea;
import ru.anafro.quark.server.debug.ui.components.TextField;
import ru.anafro.quark.server.networking.Server;

import java.awt.*;
import java.util.stream.Collectors;

public class InstructionLexerDebugFrame extends DebugFrame {
    private final Panel panel;
    private final TextArea lexerOutputArea;
    private final TextField instructionInputField;
    private final Server server;



    public InstructionLexerDebugFrame(Server server) {
        super("Instruction Lexer");

        this.server = server;

        setPreferredSize(new Dimension(600, 440)); // TODO: Move creating the main panel and setting its size to the 'DebugFrame' constructor.
        setResizable(false);

        panel = new Panel(new Rectangle(0, 0, 600, 400));

        lexerOutputArea = TextArea.console(new Rectangle(0, 0, 600, 380));
        lexerOutputArea.setEditable(false);
        lexerOutputArea.setLineWrap(true);

        instructionInputField = TextField.console(new Rectangle(0, 380, 600, 20), this::updateLexerOutput);

        panel.add(lexerOutputArea);
        panel.add(instructionInputField);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void updateLexerOutput() {
        try {
            lexerOutputArea.setText(server.getInstructionLexer().lex(instructionInputField.getText()).stream().map(InstructionToken::toString).collect(Collectors.joining("\n")));
        } catch(InstructionSyntaxException exception) {
            lexerOutputArea.setText(exception.getMessage());
        }
    }
}
