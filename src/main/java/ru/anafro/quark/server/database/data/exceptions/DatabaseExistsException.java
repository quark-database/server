package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class DatabaseExistsException extends DatabaseException {

    public DatabaseExistsException(String databaseName) {
        super(STR."The database \{databaseName} already exists.");
    }
}
