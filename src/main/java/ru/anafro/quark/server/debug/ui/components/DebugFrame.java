package ru.anafro.quark.server.debug.ui.components;

import ru.anafro.quark.server.debug.ui.exceptions.ImageForDebugFrameIconNotFoundException;
import ru.anafro.quark.server.files.Assets;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class DebugFrame extends JFrame {
    public DebugFrame(String title) {
        setTitle(title + " - Quark Server (Debug)");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        String logoImagePath = Assets.get("Icons/Logo.png");

        try {
            setIconImage(ImageIO.read(new File(logoImagePath)));
        } catch (IOException exception) {
            throw new ImageForDebugFrameIconNotFoundException(logoImagePath);
        }
    }

    public void open() {
        setVisible(true);
    }
}
