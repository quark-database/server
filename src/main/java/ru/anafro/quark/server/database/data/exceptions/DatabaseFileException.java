package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class DatabaseFileException extends DatabaseException {
    public DatabaseFileException(String message) {
        super(message);
    }
}
