package ru.anafro.quark.server.networking.exceptions;

public class BadServerConfigurationFormatException extends NetworkingException {
    public BadServerConfigurationFormatException(String path, Throwable becauseOf) {
        super("Configuration file " + path + " is malformed, because of " + becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
