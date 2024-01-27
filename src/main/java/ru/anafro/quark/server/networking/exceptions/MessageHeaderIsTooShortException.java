package ru.anafro.quark.server.networking.exceptions;

/**
 * MessageHeaderIsBrokenException should be thrown, when message
 * cannot be created, because its header is too short.
 */
public class MessageHeaderIsTooShortException extends NetworkingException {
    public MessageHeaderIsTooShortException(int headerLengthInBytes, int actualHeaderLength) {
        super(STR."Bytes cannot be converted to message, because an excepted header length is \{headerLengthInBytes}, but only \{actualHeaderLength} bytes were received.");
    }
}
