package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.utils.containers.Lists;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class VariableFolder {
    public static final String NAME = "Variables";
    private final Table table;
    private final File folder;

    public VariableFolder(Table table) {
        this.table = table;
        this.folder = Paths.get(table.getDatabase().getFolder().getAbsolutePath(), table.getName(), NAME).toFile();
    }

    public File getFolder() {
        return folder;
    }

    public Table getTable() {
        return table;
    }

    public VariableFile getVariable(String variableName) {
        return new VariableFile(table, variableName);
    }

    public ArrayList<VariableFile> all() {
        var tables = Lists.<VariableFile>empty();

        for(var tableFolder : Objects.requireNonNull(folder.listFiles())) {
            if(tableFolder.isDirectory()) {
                tables.add(getVariable(tableFolder.getName()));
            }
        }

        return tables;
    }
}
