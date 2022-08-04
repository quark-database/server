package ru.anafro.quark.server.networking.exceptions;

public class WrongMessageFormatException extends NetworkingException {
    public WrongMessageFormatException(Throwable becauseOf) {
        super(becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
