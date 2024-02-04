package ru.anafro.quark.server.utils.files.exceptions;

import ru.anafro.quark.server.utils.exceptions.UtilityException;

public abstract class FileException extends UtilityException {

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(String message) {
        super(message);
    }
}
