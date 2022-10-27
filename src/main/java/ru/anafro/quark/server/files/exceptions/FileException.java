package ru.anafro.quark.server.files.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class FileException extends QuarkException {
    public FileException(String message) {
        super(message);
    }
}
