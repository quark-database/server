package ru.anafro.quark.server.debug.components;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.debug.exceptions.ImageForDebugFrameIconNotFoundException;
import ru.anafro.quark.server.files.Assets;
import ru.anafro.quark.server.networking.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class DebugFrame extends JFrame {
    private static final int EXTRA_FRAME_HEIGHT = 40;
    protected final Server server;
    protected Panel panel;


    public DebugFrame(String title, int width, int height) {
        this.server = Quark.server();
        this.panel = new Panel(new Rectangle(0, 0, width, height));

        setTitle(title + " - Quark Server (Debug)");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setPreferredSize(new Dimension(width, height + EXTRA_FRAME_HEIGHT));
        setResizable(false);

        constructInterface();

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);

        String logoImagePath = Assets.get("Icons/Logo.png");
        try {
            setIconImage(ImageIO.read(new File(logoImagePath)));
        } catch (IOException exception) {
            throw new ImageForDebugFrameIconNotFoundException(logoImagePath);
        }
    }

    protected abstract void constructInterface();

    public void add(JComponent component) {
        panel.add(component);
    }

    public void open() {
        setVisible(true);
    }
}
