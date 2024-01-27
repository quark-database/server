package ru.anafro.quark.server.database.data.files;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.utils.files.Directory;

import java.util.Iterator;
import java.util.List;

public class VariableDirectory implements Iterable<TableVariable> {
    public static final String NAME = "Variables";
    private final Table table;
    private final Directory directory;

    public VariableDirectory(Table table) {
        this.table = table;
        this.directory = table.getDatabase().getDirectory().getDirectory(table.getName(), NAME);
    }

    public TableVariable getVariable(String variableName) {
        return new TableVariable(table, variableName);
    }

    public List<TableVariable> all() {
        return directory.getDirectories().stream().map(directory -> getVariable(directory.getName())).toList();
    }

    public boolean hasVariable(String variableName) {
        return all().stream().anyMatch(variable -> variableName.equals(variable.getName()));
    }

    public boolean missingVariable(String variableName) {
        return !hasVariable(variableName);
    }

    @NotNull
    @Override
    public Iterator<TableVariable> iterator() {
        return all().iterator();
    }
}
