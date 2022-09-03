package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.utils.containers.Lists;

import java.io.File;
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

    public Table getTable(String name) {
        return new Table(name, this);
    }

    public List<Table> allTables() {
        var tables = new ArrayList<Table>();

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
}
