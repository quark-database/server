package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.files.DatabasesDirectory;
import ru.anafro.quark.server.utils.files.Directory;

import java.util.List;

public class Database {
    private final Directory directory;

    private Database(Directory directory) {
        this.directory = directory;
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
        var databaseDirectory = DatabasesDirectory.getInstance().createDirectory(databaseName);
        return new Database(databaseDirectory);
    }

    public static void delete(String databaseName) {
        byName(databaseName).delete();
    }

    public Table getTable(String tableName) {
        return new Table(this.getName(), tableName);
    }

    public List<Table> allTables() {
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
        var sibling = directory.getSibling(destinationName);
        directory.copy(sibling.getPath());

        return byName(destinationName);
    }

    public void rename(String newName) {
        directory.moveTo(newName);
    }
}
