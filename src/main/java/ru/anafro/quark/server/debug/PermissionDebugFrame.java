package ru.anafro.quark.server.debug;

import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.debug.components.TextArea;
import ru.anafro.quark.server.debug.components.TextField;
import ru.anafro.quark.server.security.TokenPermission;

import java.awt.*;

public class PermissionDebugFrame extends DebugFrame {
    private TextField neededPermissionField;
    private TextField tokenPermissionField;
    private TextArea outputArea;

    public PermissionDebugFrame() {
        super("Permissions Comparison", "permissions", 400, 100);
    }

    @Override
    protected void constructInterface() {
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

        if(tokenPermission.includesPermission(tokenPermissionString)) {
            outputArea.setForeground(new Color(0x007700));
            outputArea.setText("Token is allowed to do things with permission " + neededPermissionString);
        } else {
            outputArea.setForeground(new Color(0x770000));
            outputArea.setText("Token is not allowed to such thing with permission " + neededPermissionString);
        }
    }

    public TextField getNeededPermissionField() {
        return neededPermissionField;
    }

    public TextField getTokenPermissionField() {
        return tokenPermissionField;
    }

    public TextArea getOutputArea() {
        return outputArea;
    }
}
