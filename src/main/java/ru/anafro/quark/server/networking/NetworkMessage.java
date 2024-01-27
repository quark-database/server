package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.networking.exceptions.MessageCannotBeCollectedException;
import ru.anafro.quark.server.utils.types.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NetworkMessage {
    public static final int HEADER_LENGTH = Integer.BYTES;
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    private final byte[] bytes;

    public NetworkMessage(String message) {
        this.bytes = message.getBytes();
    }

    public static NetworkMessage collect(InputStream stream) {
        try {
            byte[] lengthBytes = new byte[Integer.BYTES];
            for (int index = 0; index < lengthBytes.length; index++) {
                lengthBytes[index] = (byte) stream.read();
            }

            int length = Bytes.toInteger(lengthBytes);
            byte[] messageBytes = new byte[length];
            for (int index = 0; index < length; index++) {
                messageBytes[index] = (byte) stream.read();
            }

            return new NetworkMessage(encode(messageBytes));
        } catch (IOException exception) {
            throw new MessageCannotBeCollectedException(exception);
        }
    }

    public static String collectString(InputStream stream) {
        return collect(stream).getContents();
    }

    private static String encode(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    public String getContents() {
        return encode(bytes);
    }

    public byte[] toByteArray() {
        byte[] byteMessage = new byte[HEADER_LENGTH + bytes.length];
        byte[] messageHeader = Bytes.fromInteger(bytes.length);

        System.arraycopy(messageHeader, 0, byteMessage, 0, messageHeader.length);
        System.arraycopy(bytes, 0, byteMessage, messageHeader.length, bytes.length + messageHeader.length - messageHeader.length);

        return byteMessage;
    }
}
