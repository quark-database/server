package ru.anafro.quark.server.networking.exceptions;

public class MessageCannotBeCollectedException extends NetworkingException {

    public MessageCannotBeCollectedException(Throwable becauseOf) {
        super(becauseOf.getClass().getSimpleName() + ": " + becauseOf.getMessage());
    }
}
