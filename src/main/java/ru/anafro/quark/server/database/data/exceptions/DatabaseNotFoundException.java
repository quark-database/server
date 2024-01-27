package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class DatabaseNotFoundException extends DatabaseException {
    public DatabaseNotFoundException(String name) {
        super(STR."There is no database with name \{name}.");
    }
}
