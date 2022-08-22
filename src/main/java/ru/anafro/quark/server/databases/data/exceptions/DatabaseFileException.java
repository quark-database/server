package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class DatabaseFileException extends DatabaseException {
    public DatabaseFileException(String message) {
        super(message);
    }
}
