package ru.anafro.quark.server.debug.ui.components;

import javax.swing.*;
import java.awt.*;

public class TextArea extends JTextArea {
    public TextArea(Rectangle boundaries, Font font, Color background, Color foreground) {
        setBounds(boundaries);
        setBackground(background);
        setForeground(foreground);
        setFont(font);
        setLineWrap(true);
    }

    public static TextArea console(int locationX, int locationY, int width, int height) {
        return new TextArea(new Rectangle(locationX, locationY, width, height), DefaultInterfaceProperties.CONSOLE_FONT, DefaultInterfaceProperties.COLOR_WHITE, DefaultInterfaceProperties.COLOR_DARKEST_GRAY);
    }
}
