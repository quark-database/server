package ru.anafro.quark.server.networking.exceptions;

public class ServerConfigurationCannotBeLoadedException extends NetworkingException {
    public ServerConfigurationCannotBeLoadedException(String path, Throwable becauseOf) {
        super("Configuration file " + path + " cannot be loaded, because of " + becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
