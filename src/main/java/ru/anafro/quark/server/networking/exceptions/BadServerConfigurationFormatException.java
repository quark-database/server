package ru.anafro.quark.server.networking.exceptions;

/**
 * This exception is thrown when configuration format
 * is bad.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class BadServerConfigurationFormatException extends NetworkingException {

    /**
     * Creates a new exception instance.
     *
     * @param   path the path of the configuration.
     * @param   becauseOf the exception thrown on configuration parsing.
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BadServerConfigurationFormatException(String path, Throwable becauseOf) {
        super("Configuration file " + path + " is malformed, because of " + becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
