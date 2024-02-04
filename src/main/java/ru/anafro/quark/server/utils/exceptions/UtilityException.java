package ru.anafro.quark.server.utils.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class UtilityException extends QuarkException {
    public UtilityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityException(String message) {
        super(message);
    }
}
