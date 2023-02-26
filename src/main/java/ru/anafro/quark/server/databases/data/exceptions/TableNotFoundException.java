package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class TableNotFoundException extends DatabaseException {
    public TableNotFoundException(CompoundedTableName name) {
        super("There is no table with name %s in database %s.".formatted(name.getTableName(), name.getDatabaseName()));
    }

    public TableNotFoundException(String compoundedName) {
        this(new CompoundedTableName(compoundedName));
    }
}
