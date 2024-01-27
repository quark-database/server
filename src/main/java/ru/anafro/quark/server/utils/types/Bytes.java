package ru.anafro.quark.server.utils.types;

import ru.anafro.quark.server.utils.types.exceptions.WrongByteCountException;

import java.nio.ByteBuffer;

/**
 * Helps to convert byte arrays to primitive types and vise versa.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @since Quark 1.1
 */
public final class Bytes {

    /**
     * This private constructor of Bytes class <strong>MUST NOT</strong> be ever
     * called, because Bytes is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Bytes() {
        //
    }

    public static int toInteger(byte[] bytes) {
        if (bytes.length != Integer.BYTES) {
            throw new WrongByteCountException(bytes.length, Integer.BYTES);
        }

        ByteBuffer integerBytesBuffer = ByteBuffer.allocate(Integer.BYTES);
        integerBytesBuffer.put(bytes);
        integerBytesBuffer.flip();

        return integerBytesBuffer.getInt();
    }

    public static byte[] fromInteger(int value) {
        ByteBuffer integerBytesBuffer = ByteBuffer.allocate(Integer.BYTES);
        integerBytesBuffer.putInt(value);

        return integerBytesBuffer.array();
    }
}
