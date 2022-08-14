package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;
import ru.anafro.quark.server.plugins.events.Event;
import ru.anafro.quark.server.plugins.exceptions.PluginException;

import java.lang.reflect.Method;

public class PluginEventException extends PluginException {
    public PluginEventException(Exception causedBy, Event event, Plugin plugin, Method method) {
        super("An uncaught exception %s occurred while firing an event %s in plugin %s's method %s: %s".formatted(
                causedBy.getClass().getName(),
                event.getClass().getName(),
                plugin.getName(),
                method.getName(),
                causedBy.getMessage()
        ));

        initCause(causedBy);
    }
}
