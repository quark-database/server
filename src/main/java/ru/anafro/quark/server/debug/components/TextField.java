package ru.anafro.quark.server.debug.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextField extends JTextField {
    protected final Runnable onEdit;

    public TextField(Rectangle boundaries, Font font, Color background, Color foreground, Runnable onEdit) {
        this.onEdit = onEdit;

        setBounds(boundaries);
        setFont(font);
        setForeground(foreground);
        setBackground(background);

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onEdit.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onEdit.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onEdit.run();
            }
        });
    }

    public static TextField console(int locationX, int locationY, int width, int height, Runnable onEdit) {
        return new TextField(new Rectangle(locationX, locationY, width, height), DefaultInterfaceProperties.CONSOLE_FONT, DefaultInterfaceProperties.COLOR_WHITE, DefaultInterfaceProperties.COLOR_BLACK, onEdit);
    }
}
