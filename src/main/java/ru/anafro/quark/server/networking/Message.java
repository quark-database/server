package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.networking.exceptions.MessageCannotBeCollectedException;
import ru.anafro.quark.server.networking.exceptions.MessageHeaderIsTooShortException;
import ru.anafro.quark.server.utils.types.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Message {
    public static final int HEADER_LENGTH = Integer.BYTES;
    public Charset charset = StandardCharsets.UTF_8;
    private final byte[] bytes;

    public Message(byte[] rawMessage) {
        if(rawMessage.length < HEADER_LENGTH) {
            throw new MessageHeaderIsTooShortException(HEADER_LENGTH, rawMessage.length);
        }

        this.bytes = Arrays.copyOfRange(rawMessage, HEADER_LENGTH, rawMessage.length);
    }

    public Message(String message) {
        this.bytes = message.getBytes();
    }

    public static Message collect(InputStream stream) {
        try {
            byte[] lengthBytes = new byte[Integer.BYTES];
            for(int index = 0; index < lengthBytes.length; index++) {
                lengthBytes[index] = (byte) stream.read();
            }

            int length = Bytes.toInteger(lengthBytes);
            byte[] messageBytes = new byte[length];
            for(int index = 0; index < length; index++) {
                messageBytes[index] = (byte) stream.read();
            }

            return new Message(new String(messageBytes, StandardCharsets.UTF_8));
        } catch (IOException exception) {
            throw new MessageCannotBeCollectedException(exception);
        }
    }

    public long getLength() {
        return bytes.length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getContents() {
        return new String(bytes, charset);
    }

    public byte[] buildByteMessage() {
        byte[] byteMessage = new byte[HEADER_LENGTH + bytes.length];
        byte[] messageHeader = Bytes.fromInteger(bytes.length);

        for(int index = 0; index < messageHeader.length; index++) {
            byteMessage[index] = messageHeader[index];
        }

        for(int index = messageHeader.length; index < bytes.length + messageHeader.length; index++) {
            byteMessage[index] = bytes[index - messageHeader.length];
        }

        return byteMessage;
    }
}
