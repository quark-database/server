package ru.anafro.quark.server.databases.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class DatabaseException extends QuarkException {
    public DatabaseException(String message) {
        super(message);
    }
}
