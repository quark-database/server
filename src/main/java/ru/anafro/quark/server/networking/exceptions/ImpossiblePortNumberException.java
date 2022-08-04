package ru.anafro.quark.server.networking.exceptions;

/**
 * ImpossiblePortNumberException should be thrown when provided
 * port number is incorrect. It means that it is less than the
 * first port number (usually 0) or higher than the last port number
 * (usually 65535).
 */
public class ImpossiblePortNumberException extends NetworkingException {

    public ImpossiblePortNumberException(int whichPortIsImpossible) {
        super("This port is impossible: " + whichPortIsImpossible);
    }
}
