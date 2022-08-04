package ru.anafro.quark.server.networking.exceptions;

/**
 * ServerCannotBeRunTwiceException should be thrown when networking
 * server is being tried to run ones more after successful launch.
 */
public class ServerCannotBeRunTwiceException extends NetworkingException {
    public ServerCannotBeRunTwiceException() {
        super("Server has already started and cannot be started again.");
    }
}
