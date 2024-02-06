package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class BadFinderException extends DatabaseException {
    public BadFinderException(Table table, TableRecordFinder finder) {
        super(STR."The column \{finder.columnName()} in table \{table.getName()} is not unique.");
    }
}
