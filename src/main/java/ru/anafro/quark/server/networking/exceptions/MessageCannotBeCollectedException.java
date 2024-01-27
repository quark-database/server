package ru.anafro.quark.server.networking.exceptions;

public class MessageCannotBeCollectedException extends NetworkingException {

    public MessageCannotBeCollectedException(Throwable becauseOf) {
        super(STR."\{becauseOf.getClass().getSimpleName()}: \{becauseOf.getMessage()}");
    }
}
