package ru.anafro.quark.server.utils.types.exceptions;

import ru.anafro.quark.server.utils.types.Bytes;

/**
 * {@link WrongByteCountException} used only inside {@link Bytes} class
 * when bytes cannot be converted to the desired type.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see Bytes
 * @since Quark 1.1
 */
public class WrongByteCountException extends TypeException {

    /**
     * Creates an exception instance.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Bytes
     * @since Quark 1.1
     */
    public WrongByteCountException(int bytesNeeded, int actualByteCount) {
        super(STR."Byte count is wrong: \{bytesNeeded} needed, but there are \{actualByteCount}");
    }
}
