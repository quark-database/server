package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;
import ru.anafro.quark.server.plugins.events.Event;

import java.lang.reflect.Method;

public class PluginReflectionException extends PluginException {
    public PluginReflectionException(ReflectiveOperationException causedBy, Event event, Plugin plugin, Method method) {
        super("An uncaught reflection exception %s occurred while firing an event %s in plugin %s's method %s: %s".formatted(
                causedBy.getClass().getName(),
                event.getClass().getName(),
                plugin.getName(),
                method.getName(),
                causedBy.getMessage()
        ));
        initCause(causedBy);
    }
}
