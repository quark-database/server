package ru.anafro.quark.server.debug.components;

import ru.anafro.quark.server.files.AssetDirectory;
import ru.anafro.quark.server.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Debugger extends JFrame {
    private static final int EXTRA_FRAME_HEIGHT = 40;
    protected final Logger logger = new Logger(getClass());
    protected final Panel panel;
    protected final String name;


    public Debugger(String title, String name, int width, int height) {
        this.name = name;
        this.panel = new Panel(new Rectangle(0, 0, width, height));

        setTitle(STR."\{title} - Quark Server (Debug)");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setPreferredSize(new Dimension(width, height + EXTRA_FRAME_HEIGHT));
        setResizable(false);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);

        String logoImagePath = AssetDirectory.getInstance().getAbsoluteFilePath("Icons", "Logo.png");

        try {
            setIconImage(ImageIO.read(new File(logoImagePath)));
        } catch (IOException exception) {
            logger.debug(STR."Can't find the Quark logo at path \{logoImagePath}.");
        }
    }

    public void add(JComponent component) {
        panel.add(component);
    }

    public void open() {
        if (isVisible()) {
            pullToTop();
        } else {
            setVisible(true);
        }

    }

    @Override
    public String getName() {
        return name;
    }

    public void pullToTop() {
        EventQueue.invokeLater(() -> {
            setState(NORMAL);
            toFront();
            repaint();
        });
    }
}
