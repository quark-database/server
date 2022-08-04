package ru.anafro.quark.server.types;

import ru.anafro.quark.server.types.exceptions.WrongByteCountException;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class Bytes {
    private Bytes() {}

    public static long toLong(byte[] bytes) {
        if(bytes.length != Long.BYTES) {
            throw new WrongByteCountException(Long.BYTES, bytes.length);
        }

        ByteBuffer longBytesBuffer = ByteBuffer.allocate(Long.BYTES);
        longBytesBuffer.put(bytes);
        longBytesBuffer.flip();

        return longBytesBuffer.getLong();
    }

    public static byte[] fromLong(long value) {
        ByteBuffer longBytesBuffer = ByteBuffer.allocate(Long.BYTES);
        longBytesBuffer.putLong(value);

        return longBytesBuffer.array();
    }
}
