package ru.anafro.quark.server.networking;

import ru.anafro.quark.server.networking.exceptions.MessageCannotBeCollectedException;
import ru.anafro.quark.server.networking.exceptions.MessageHeaderIsTooShortException;
import ru.anafro.quark.server.types.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Message {
    public static final int HEADER_LENGTH = Long.BYTES;
    public Charset charset = StandardCharsets.UTF_8;
    private final long length;
    private final byte[] bytes;

    public Message(byte[] rawMessage) {
        if(rawMessage.length < HEADER_LENGTH) {
            throw new MessageHeaderIsTooShortException(HEADER_LENGTH, rawMessage.length);
        }

        this.length = Bytes.toLong(Arrays.copyOfRange(rawMessage, 0, HEADER_LENGTH));
        this.bytes = Arrays.copyOfRange(rawMessage, HEADER_LENGTH, rawMessage.length);
    }

    public Message(String message) {
        this.length = message.length();
        this.bytes = message.getBytes();
    }

    public static Message collect(InputStream stream) {
        try {
            return new Message(stream.readAllBytes());
        } catch (IOException exception) {
            throw new MessageCannotBeCollectedException(exception);
        }
    }

    public long getLength() {
        return length;
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
        byte[] messageHeader = Bytes.fromLong(length);

        System.arraycopy(messageHeader, 0, byteMessage, 0, messageHeader.length);

        if (bytes.length >= messageHeader.length)
            System.arraycopy(bytes, messageHeader.length, byteMessage, messageHeader.length, bytes.length - messageHeader.length);

        return byteMessage;
    }
}
