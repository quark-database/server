package ru.anafro.quark.server.networking.exceptions;

public class ServerConfigurationCannotBeSavedException extends NetworkingException {
    public ServerConfigurationCannotBeSavedException(Throwable becauseOf) {
        super("Server configuration cannot be saved, because: " + becauseOf);
        initCause(becauseOf);
    }
}
