package ru.anafro.quark.server.networking.exceptions;

/**
 * PortInUnavailableException should be thrown when provided port
 * is unavailable. It usually means that port is already used
 * by another program, or it is reserved for OS usage.
 */
public class PortIsUnavailableException extends NetworkingException {
    public PortIsUnavailableException(int whichPortIsAlreadyUsed) {
        super(STR."Port is used or unavailable: \{whichPortIsAlreadyUsed}");
    }
}
