package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.security.TokenPermission;

import java.awt.*;

public class PermissionDebugger extends Debugger {
    private final TextField neededPermissionField;
    private final TextField tokenPermissionField;
    private final TextArea outputArea;

    public PermissionDebugger() {
        super("Permissions Comparison", "permissions", 400, 100);

        neededPermissionField = TextField.console(0, 0, 400, 20, this::comparePermissions);
        tokenPermissionField = TextField.console(0, 20, 400, 20, this::comparePermissions);
        outputArea = TextArea.console(0, 40, 400, 60);

        add(neededPermissionField);
        add(tokenPermissionField);
        add(outputArea);
    }

    private void comparePermissions() {
        var neededPermissionString = neededPermissionField.getText();
        var tokenPermissionString = tokenPermissionField.getText();

        var tokenPermission = new TokenPermission(neededPermissionString);

        if (tokenPermission.includesPermission(tokenPermissionString)) {
            outputArea.setForeground(new Color(0x007700));
            outputArea.setText(STR."Token is allowed to do things with permission \{neededPermissionString}");
        } else {
            outputArea.setForeground(new Color(0x770000));
            outputArea.setText(STR."Token is not allowed to such thing with permission \{neededPermissionString}");
        }
    }
}