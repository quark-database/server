package ru.anafro.quark.server.debug.components;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(Rectangle boundaries) {
        super(null);
        setBounds(boundaries);
    }
}
