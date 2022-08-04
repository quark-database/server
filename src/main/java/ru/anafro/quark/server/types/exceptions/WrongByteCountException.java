package ru.anafro.quark.server.types.exceptions;

public class WrongByteCountException extends TypeException {
    public WrongByteCountException(long bytesNeeded, long actualByteCount) {
        super("Byte count is wrong: " + bytesNeeded + " needed, but there are " + actualByteCount);
    }
}
