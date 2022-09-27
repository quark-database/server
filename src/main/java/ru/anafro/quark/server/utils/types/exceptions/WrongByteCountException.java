package ru.anafro.quark.server.utils.types.exceptions;

import ru.anafro.quark.server.utils.types.Bytes;

/**
 * {@link WrongByteCountException} used only inside {@link Bytes} class
 * when bytes cannot be converted to the desired type.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Bytes
 */
public class WrongByteCountException extends TypeException {

    /**
     * Creates an exception instance.
     *
     * @param bytesNeeded     bytes count that was needed for a type.
     * @param actualByteCount bytes count that was received instead.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Bytes
     */
    public WrongByteCountException(long bytesNeeded, long actualByteCount) {
        super("Byte count is wrong: " + bytesNeeded + " needed, but there are " + actualByteCount);
    }
}
