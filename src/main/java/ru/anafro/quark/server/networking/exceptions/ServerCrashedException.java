package ru.anafro.quark.server.networking.exceptions;

public class ServerCrashedException extends NetworkingException {
    public ServerCrashedException(Throwable becauseOf) {
        super(becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
