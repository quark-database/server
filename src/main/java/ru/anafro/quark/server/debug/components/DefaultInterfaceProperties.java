package ru.anafro.quark.server.debug.components;

import java.awt.*;

public final class DefaultInterfaceProperties {
    private DefaultInterfaceProperties() {
        // Preventing making objects.
    }

    public static final Color COLOR_BLUE = new Color(0x0081FF);
    public static final Color COLOR_RED = new Color(0xFF1800);
    public static final Color COLOR_BLACK = new Color(0x000000);
    public static final Color COLOR_DARKEST_GRAY = new Color(0x333333);
    public static final Color COLOR_GRAY = new Color(0x999999);
    public static final Color COLOR_LIGHTEST_GRAY = new Color(0xF2F2F2);
    public static final Color COLOR_WHITE = new Color(0xFFFFFF);

    public static final Font FONT = new Font("Calibri", Font.PLAIN, 12);
    public static final Font CONSOLE_FONT = new Font("Consolas", Font.PLAIN, 12);
    public static final Font TITLE_FONT = new Font("Gilroy Medium", Font.PLAIN, 18);
}
