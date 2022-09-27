package ru.anafro.quark.server.networking.exceptions;

/**
 * {@link WrongMessageFormatException} is thrown when byte message
 * received from the client is incorrect, malformed, or reading
 * the message from TCP sockets was interrupted.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class WrongMessageFormatException extends NetworkingException {

    /**
     * Creates a new exception instance.
     *
     * @param becauseOf the exception caused
     */
    public WrongMessageFormatException(Throwable becauseOf) {
        super(becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
