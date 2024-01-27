package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class TableNotFoundException extends DatabaseException {
    public TableNotFoundException(TableName name) {
        super("There is no table with name %s in database %s.".formatted(name.getTableName(), name.getDatabaseName()));
    }

    public TableNotFoundException(String compoundedName) {
        this(new TableName(compoundedName));
    }
}
