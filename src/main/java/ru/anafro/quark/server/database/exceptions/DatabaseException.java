package ru.anafro.quark.server.database.exceptions;

import ru.anafro.quark.server.exceptions.QuarkException;

public class DatabaseException extends QuarkException {
    public DatabaseException(String message) {
        super(message);
    }
}
