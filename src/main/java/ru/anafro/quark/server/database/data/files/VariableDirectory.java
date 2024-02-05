package ru.anafro.quark.server.database.data.files;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.utils.files.Directory;

import java.util.Iterator;
import java.util.List;

import static ru.anafro.quark.server.utils.files.filters.ExtensionInclusionFileFilter.withExtension;

public class VariableDirectory implements Iterable<TableVariable> {
    public static final String NAME = "Variables";
    private final Table table;
    private final Directory directory;

    public VariableDirectory(Table table) {
        this.table = table;
        this.directory = new Directory(table.getDatabase().getDirectory().getFilePath(table.getName(), NAME), withExtension("qvariable"));
    }

    public TableVariable getVariable(String variableName) {
        return new TableVariable(table, variableName);
    }

    public List<TableVariable> all() {
        return directory.files().map(file -> new TableVariable(table, file.getNameWithoutExtension())).toList();
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
