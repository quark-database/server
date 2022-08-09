package ru.anafro.quark.server.debug.ui.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class UIException extends QuarkException {
    public UIException(String message) {
        super(message);
    }
}
