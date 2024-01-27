package ru.anafro.quark.server.files;

import ru.anafro.quark.server.database.data.TableName;
import ru.anafro.quark.server.utils.files.Directory;

public class DatabasesDirectory extends Directory {
    private static final DatabasesDirectory instance = new DatabasesDirectory("Databases");

    public DatabasesDirectory(String relativePath) {
        super(relativePath);
    }

    public static DatabasesDirectory getInstance() {
        return instance;
    }

    public Directory getDatabaseDirectory(String databaseName) {
        return getDirectory(databaseName);
    }

    public Directory getTableDirectory(String databaseName, String tableName) {
        return getDirectory(databaseName, tableName);
    }

    public Directory getTableDirectory(TableName compoundedTableName) {
        var databaseName = compoundedTableName.getDatabaseName();
        var tableName = compoundedTableName.getTableName();

        return getTableDirectory(databaseName, tableName);
    }
}
