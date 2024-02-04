package ru.anafro.quark.server.debug.components;

import ru.anafro.quark.server.files.AssetDirectory;
import ru.anafro.quark.server.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Debugger {
    private static final int EXTRA_FRAME_HEIGHT = 40;
    protected final Logger logger = new Logger(getClass());
    protected final JFrame frame;
    protected final Panel panel;
    protected final String name;


    public Debugger(String title, String name, int width, int height) {
        this.name = name;
        this.panel = new Panel(new Rectangle(0, 0, width, height));
        this.frame = new JFrame(STR."\{title} - Quark Server (Debug)");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height + EXTRA_FRAME_HEIGHT));
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        var logoImagePath = AssetDirectory.getInstance().getAbsoluteFilePath("Icons", "Logo.png");

        try {
            frame.setIconImage(ImageIO.read(new File(logoImagePath)));
        } catch (IOException exception) {
            logger.debug(STR."Can't find the Quark logo at path \{logoImagePath}.");
        }
    }

    public static boolean isSupported() {
        return !GraphicsEnvironment.isHeadless();
    }

    public void add(JComponent component) {
        panel.add(component);
    }

    public void open() {
        if (frame.isVisible()) {
            pullToTop();
        } else {
            frame.setVisible(true);
        }

    }

    public String getName() {
        return name;
    }

    public void pullToTop() {
        EventQueue.invokeLater(() -> {
            frame.setState(Frame.NORMAL);
            frame.toFront();
            frame.repaint();
        });
    }
}
