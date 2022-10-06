package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.CompoundedTableName;

public class WrongCompoundedTableNameException extends DatabaseFileException {
    public WrongCompoundedTableNameException(String wrongCompoundedName) {
        super("The compounded name '%s' is wrong, because it must contain one symbol '%s', which separates the database name and table name. Use this form: DatabaseName%sTableName".formatted(
                wrongCompoundedName,
                CompoundedTableName.SEPARATOR,
                CompoundedTableName.SEPARATOR
        ));
    }
}
