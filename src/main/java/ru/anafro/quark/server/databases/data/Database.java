package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.utils.containers.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Database {
    private final File folder;
    private final String name;

    private Database(String folderPath) {
        this.folder = new File(folderPath);
        this.name = folder.getName();
    }

    public static Database byName(String databaseName) {
        return new Database(Databases.get(databaseName));
    }

    public static List<Database> all() {
        var databases = Lists.<Database>empty();

        for(var databaseFolder : Objects.requireNonNull(new File(Databases.FOLDER).listFiles())) {
            if(databaseFolder.isDirectory()) {
                databases.add(new Database(databaseFolder.getAbsolutePath()));
            }
        }

        return databases;
    }

    public static boolean exists(String databaseName) {
        var databaseDirectory = new File(Databases.get(databaseName));

        return databaseDirectory.exists() && databaseDirectory.isDirectory();
    }

    public static void create(String databaseName) {
        boolean ignored = new File(Databases.get(databaseName)).mkdir();
    }

    public static void delete(String databaseName) {
        try {
            Files.walk(Path.of(Databases.get(databaseName)))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException exception) {
            throw new DatabaseFileException("Database %s cannot be deleted, because of: " + exception);
        }
    }

    public Table getTable(String tableName) {
        return new Table(this.getName(), tableName);
    }

    public List<Table> allTables() {
        var tables = Lists.<Table>empty();

        for(var tableFolder : Objects.requireNonNull(folder.listFiles())) {
            if(tableFolder.isDirectory()) {
                tables.add(getTable(tableFolder.getName()));
            }
        }

        return tables;
    }

    public String getName() {
        return name;
    }

    public File getFolder() {
        return folder;
    }

    public boolean hasTable(String tableName) {
        return Table.exists(new CompoundedTableName(this.getName(), tableName).toCompoundedString());
    }
}
