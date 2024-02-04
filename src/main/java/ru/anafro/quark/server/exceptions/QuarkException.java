package ru.anafro.quark.server.exceptions;

public class QuarkException extends RuntimeException {
    public QuarkException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuarkException(String message) {
        super(message);
    }
}
