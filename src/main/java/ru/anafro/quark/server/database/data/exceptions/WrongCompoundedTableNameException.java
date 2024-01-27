package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.TableName;

public class WrongCompoundedTableNameException extends DatabaseFileException {
    public WrongCompoundedTableNameException(String wrongCompoundedName) {
        super("The compounded name '%s' is wrong, because it must contain one symbol '%s', which separates the database name and table name. Use this form: DatabaseName%sTableName".formatted(
                wrongCompoundedName,
                TableName.SEPARATOR,
                TableName.SEPARATOR
        ));
    }
}
