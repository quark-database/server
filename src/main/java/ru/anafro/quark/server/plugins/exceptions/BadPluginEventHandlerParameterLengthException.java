package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;

import java.lang.reflect.Method;

/**
 * This exception is thrown when event handled method in a plugin
 * got a bad length of parameters.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class BadPluginEventHandlerParameterLengthException extends PluginException {

    /**
     * Creates an exception instance.
     *
     * @param plugin a plugin with a method with a bad parameter length.
     * @param method a method with a bad length.
     *
     * @since Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BadPluginEventHandlerParameterLengthException(Plugin plugin, Method method) {
        super("The plugin %s contains an event handler method %s, but it has %d arguments instead of %d. Please, make sure that this method has only one argument - an event.".formatted(
                plugin.getName(),
                method.getName(),
                method.getParameterCount(),
                1
        ));
    }
}
