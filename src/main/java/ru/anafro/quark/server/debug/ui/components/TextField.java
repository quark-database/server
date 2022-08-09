package ru.anafro.quark.server.debug.ui.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextField extends JTextField {
    protected Runnable onEdit;

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

    public static TextField console(Rectangle boundaries, Runnable onEdit) {
        return new TextField(boundaries, DefaultInterfaceProperties.CONSOLE_FONT, DefaultInterfaceProperties.COLOR_WHITE, DefaultInterfaceProperties.COLOR_BLACK, onEdit);
    }

    public Runnable getOnEdit() {
        return onEdit;
    }

    public void setOnEdit(Runnable onEdit) {
        this.onEdit = onEdit;
    }
}
