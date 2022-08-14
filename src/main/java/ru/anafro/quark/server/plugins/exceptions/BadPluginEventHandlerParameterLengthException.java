package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;

import java.lang.reflect.Method;

public class BadPluginEventHandlerParameterLengthException extends PluginException {
    public BadPluginEventHandlerParameterLengthException(Plugin plugin, Method method) {
        super("The plugin %s contains an event handler method %s, but it has %d arguments instead of %d. Please, make sure that this method has only one argument - an event.".formatted(
                plugin.getName(),
                method.getName(),
                method.getParameterCount(),
                1
        ));
    }
}
