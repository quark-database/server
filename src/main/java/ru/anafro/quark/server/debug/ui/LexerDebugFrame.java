package ru.anafro.quark.server.debug.ui;

import ru.anafro.quark.server.databases.instructions.lexer.InstructionLexer;
import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.databases.instructions.lexer.exceptions.InstructionSyntaxException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.stream.Collectors;

public class LexerDebugFrame extends DebugFrame {
    private final JPanel panel = new JPanel(null);
    private final JTextArea lexerOutputArea = new JTextArea();
    private final JTextField instructionInputField = new JTextField();



    public LexerDebugFrame() {
        super("Instruction Lexer Debugging");
        setPreferredSize(new Dimension(600, 440));

        panel.setBounds(0, 0, 600, 400);

        lexerOutputArea.setBounds(0, 0, 600, 380);
        lexerOutputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        lexerOutputArea.setEditable(false);
        lexerOutputArea.setLineWrap(true);

        instructionInputField.setBounds(0, 380, 600, 20);
        instructionInputField.setFont(new Font("Consolas", Font.PLAIN, 12));

        instructionInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLexerOutput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLexerOutput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLexerOutput();
            }
        });

        panel.add(lexerOutputArea);
        panel.add(instructionInputField);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void updateLexerOutput() {
        try {
            InstructionLexer lexer = new InstructionLexer(instructionInputField.getText());
            lexer.performLexing();

            lexerOutputArea.setForeground(Color.DARK_GRAY);
            lexerOutputArea.setText("-- Lexer Output --\n" + lexer.getTokens().stream().map(InstructionToken::toString).collect(Collectors.joining("\n")));
        } catch(InstructionSyntaxException exception) {
            lexerOutputArea.setForeground(new Color(200, 20, 80));
            lexerOutputArea.setText(exception.getMessage());
        }
    }
}
