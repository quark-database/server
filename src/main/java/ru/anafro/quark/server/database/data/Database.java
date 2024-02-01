package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.data.exceptions.DatabaseExistsException;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.files.DatabasesDirectory;
import ru.anafro.quark.server.utils.files.Directory;

import java.util.List;
import java.util.Objects;

public class Database {
    private static final String SYSTEM_DATABASE_NAME = Quark.NAME;
    private final Directory directory;

    private Database(Directory directory) {
        this.directory = directory;
    }

    public static Database systemDatabase() {
        return database(SYSTEM_DATABASE_NAME);
    }

    public static Database database(String databaseName) {
        return byName(databaseName);
    }

    public static Database byName(String databaseName) {
        return new Database(DatabasesDirectory.getInstance().getDatabaseDirectory(databaseName));
    }

    public static List<Database> all() {
        return DatabasesDirectory.getInstance().getDirectories().stream().map(Database::new).toList();
    }

    public static boolean exists(String databaseName) {
        return DatabasesDirectory.getInstance().hasDirectory(databaseName);
    }

    public static boolean doesntExist(String databaseName) {
        return !exists(databaseName);
    }

    public static Database createIfDoesntExist(String databaseName) {
        if (exists(databaseName)) {
            return byName(databaseName);
        }

        return create(databaseName);
    }

    public static Database create(String databaseName) {
        if (exists(databaseName)) {
            throw new DatabaseExistsException(databaseName);
        }

        var databaseDirectory = DatabasesDirectory.getInstance().createDirectory(databaseName);
        return new Database(databaseDirectory);
    }

    public Table getTable(String tableName) {
        return new Table(this.getName(), tableName);
    }

    public List<Table> tables() {
        return DatabasesDirectory
                .getInstance()
                .getDatabaseDirectory(this.getName())
                .getDirectories()
                .stream()
                .map(directory -> getTable(directory.getName()))
                .toList();
    }

    public String getName() {
        return directory.getName();
    }

    public Directory getDirectory() {
        return directory;
    }

    public void delete() {
        directory.delete();
    }

    public Database copy(String destinationName) {
        if (exists(destinationName)) {
            throw new QueryException(STR."Destination database '\{destinationName}' already exists.");
        }

        var sibling = directory.getSibling(destinationName);
        directory.copy(sibling.getPath());

        return byName(destinationName);
    }

    public void copyScheme(String destinationName) {
        copy(destinationName).clear();
    }

    public void rename(String newName) {
        directory.moveTo(newName);
    }

    public void clear() {
        tables().forEach(Table::delete);
    }

    public Table table(String tableName) {
        return getTable(tableName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database database = (Database) o;
        return Objects.equals(directory.getName(), database.getDirectory().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(directory.getName());
    }
}
