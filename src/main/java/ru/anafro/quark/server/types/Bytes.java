package ru.anafro.quark.server.types;

import ru.anafro.quark.server.types.exceptions.WrongByteCountException;

import java.nio.ByteBuffer;

/**
 * Helps to convert byte arrays to primitive types and vise versa.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @see    Bytes#fromLong(long) 
 * @see    Bytes#toLong(byte[])
 */
public final class Bytes {

    /**
     * This private constructor of Bytes class <strong>MUST NOT</strong> be ever
     * called, because Bytes is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Bytes
     */
    private Bytes() {
        //
    }

    /**
     * Converts a byte array to a long.
     * <br><br>
     *
     * A byte contains exactly 8 bytes in Java, but sometimes you
     * have not a long itself, but 8 of bytes, for example when you read a file
     * in binary format. To convert it back to long, use this method.
     * <br><br>
     *
     * Let's say, you have a byte:
     *
     * <pre>00 00 00 00 B7 D8 AF 5C</pre>
     *
     * where each pair of hex digits is a byte. Calling this method on 8-byte array
     * will convert it to a long number:
     *
     * <pre>3 084 431 196</pre>
     *
     * @param bytes                    an eight-byte array.
     * @return                         a long number from bytes passed.
     * @throws WrongByteCountException when byte array contains less or more than 8 bytes.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Bytes
     */
    public static long toLong(byte[] bytes) {
        if(bytes.length != Long.BYTES) {
            throw new WrongByteCountException(Long.BYTES, bytes.length);
        }

        ByteBuffer longBytesBuffer = ByteBuffer.allocate(Long.BYTES);
        longBytesBuffer.put(bytes);
        longBytesBuffer.flip();

        return longBytesBuffer.getLong();
    }

    /**
     * Converts a long to a byte array.
     * <br><br>
     *
     * A byte contains exactly 8 bytes in Java, and sometimes you need
     * to get the bytes of a long. To convert it back a byte array, use this method.
     * <br><br>
     *
     * Let's say, you have a long:
     *
     * <pre>3 084 431 196</pre>
     *
     * Since a byte is 8 bytes, the method will return a byte array
     * with length of eight that contains bytes of this long.
     * For the number above, the byte array would look like this:
     *
     * <pre>00 00 00 00 B7 D8 AF 5C</pre>
     *
     * @param value                    a long number to convert to bytes.
     * @return                         a byte array of this long number.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see    Bytes
     */
    public static byte[] fromLong(long value) {
        ByteBuffer longBytesBuffer = ByteBuffer.allocate(Long.BYTES);
        longBytesBuffer.putLong(value);

        return longBytesBuffer.array();
    }
}
