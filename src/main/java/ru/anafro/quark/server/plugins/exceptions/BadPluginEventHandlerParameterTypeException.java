package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;

import java.lang.reflect.Method;

public class BadPluginEventHandlerParameterTypeException extends PluginException {
    public BadPluginEventHandlerParameterTypeException(Plugin plugin, Method method) {
        super("The plugin %s contains an event handler method %s, but it a wrong type %s of the first argument. Please, make sure that this method has only one argument - an event.".formatted(
                plugin.getName(),
                method.getName(),
                method.getParameterTypes()[0].getName()
        ));
    }
}
