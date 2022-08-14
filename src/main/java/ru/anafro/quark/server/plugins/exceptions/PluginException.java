package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class PluginException extends QuarkException {
    public PluginException(String message) {
        super(message);
    }
}
