package ru.anafro.quark.server.debug.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JLabel {
    protected Runnable action;

    public Button(String text, Rectangle boundaries, Color background, Color foreground, int horizontalAlignment, Runnable action) {
        this.action = action;

        setText(text);
        setBackground(background);
        setForeground(foreground);
        setHorizontalAlignment(horizontalAlignment);
        setBorder(BorderFactory.createEmptyBorder(3, 7, 3, 7));
        setBounds(boundaries);
        setFont(DefaultInterfaceProperties.FONT);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ignored) {
                action.run();
            }
        });
    }

    public static Button primary(String text, Rectangle boundaries, Runnable action) {
        return new Button(text, boundaries, DefaultInterfaceProperties.COLOR_BLUE, DefaultInterfaceProperties.COLOR_WHITE, SwingConstants.CENTER, action);
    }

    public static Button secondary(String text, Rectangle boundaries, Runnable action) {
        return new Button(text, boundaries, DefaultInterfaceProperties.COLOR_WHITE, DefaultInterfaceProperties.COLOR_GRAY, SwingConstants.CENTER, action);
    }

    public static Button danger(String text, Rectangle boundaries, Runnable action) {
        return new Button(text, boundaries, DefaultInterfaceProperties.COLOR_RED, DefaultInterfaceProperties.COLOR_WHITE, SwingConstants.CENTER, action);
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
