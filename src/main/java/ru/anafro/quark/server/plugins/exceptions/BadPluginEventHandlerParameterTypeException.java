package ru.anafro.quark.server.plugins.exceptions;

import ru.anafro.quark.server.plugins.Plugin;

import java.lang.reflect.Method;

/**
 * This exception is thrown when event handler in a plugin
 * has a bad parameter type.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class BadPluginEventHandlerParameterTypeException extends PluginException {

    /**
     * Creates a new exception instance.
     *
     * @param   plugin a plugin with a bad method.
     * @param   method a bad method.
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BadPluginEventHandlerParameterTypeException(Plugin plugin, Method method) {
        super("The plugin %s contains an event handler method %s, but it a wrong type %s of the first argument. Please, make sure that this method has only one argument - an event.".formatted(
                plugin.getName(),
                method.getName(),
                method.getParameterTypes()[0].getName()
        ));
    }
}
