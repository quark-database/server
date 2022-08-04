package ru.anafro.quark.server.debug.ui;

import javax.swing.*;

public class DebugFrame extends JFrame {
    public DebugFrame(String title) {
        setTitle(title + " - Quark Server debug");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void open() {
        setVisible(true);
    }
}
