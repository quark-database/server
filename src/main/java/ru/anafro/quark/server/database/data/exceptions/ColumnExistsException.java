package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class ColumnExistsException extends DatabaseException {
    public ColumnExistsException(Table table, String columnName) {
        super(STR."The column \{columnName} already exists in the table \{table.getName()}.");
    }
}
