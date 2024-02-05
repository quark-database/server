package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class TableExistsException extends DatabaseException {
    public TableExistsException(String tableName) {
        super(STR."The table \{tableName} already exists.");
    }
}
