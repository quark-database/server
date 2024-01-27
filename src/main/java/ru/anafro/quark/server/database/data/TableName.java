package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.data.exceptions.WrongCompoundedTableNameException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.strings.Strings;

import java.util.regex.Pattern;

public class TableName {
    public static final String SEPARATOR = ".";
    private final String databaseName;
    private final String tableName;

    public TableName(String compoundedName) {
        if (!compoundedName.contains(SEPARATOR)) {
            throw new WrongCompoundedTableNameException(compoundedName);
        }

        var compoundedNameParts = compoundedName.split(Pattern.quote(SEPARATOR), 2);

        if (compoundedNameParts.length != 2) {
            Quark.warning("new CompoundedTableName(...) got a string '%s' with multiple separators (1 expected, but %d received). Everything after the second separator will be ignored.".formatted(compoundedName, Strings.countEntries(compoundedName, SEPARATOR)));
        }

        this.databaseName = compoundedNameParts[0];
        this.tableName = compoundedNameParts[1];
    }

    public TableName(String databaseName, String tableName) {
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public String toCompoundedString() {
        return databaseName + SEPARATOR + tableName;
    }

    @Override
    public String toString() {
        return toCompoundedString();
    }
}
