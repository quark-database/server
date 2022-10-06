package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.exceptions.VariableFileValueSettingFailedException;
import ru.anafro.quark.server.databases.data.exceptions.VariableFileWrongLinesCountException;
import ru.anafro.quark.server.databases.exceptions.VariableFileValueGettingFailedException;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class VariableFile {
    public static final String EXTENSION = ".qvariable";
    private final Table table;
    private final String name;
    private final File file;

    public VariableFile(Table table, String name) {
        this.table = table;
        this.name = name;
        this.file = new File(Path.of(table.getDatabase().getFolder().getAbsolutePath(), table.getName(), VariableFolder.NAME, name + EXTENSION).toUri());
    }

    public <T extends Entity> T get(Entity defaultValue) {
        try {
            if(!Files.exists(file.toPath())) {
                Files.createFile(file.toPath());
                set(defaultValue);
            }

            var lines = Files.readAllLines(file.toPath());

            if(lines.size() != 2) {
                throw new VariableFileWrongLinesCountException(this, lines.size());
            }

            return (T) Quark.types().get(lines.get(0)).makeEntity(lines.get(1));
        } catch (IOException exception) {
            throw new VariableFileValueGettingFailedException(this, exception);
        }
    }

    public void set(Entity newValue) {
        try {
            FileWriter variableVariable = new FileWriter(file, false);

            var variableFileContent = new TextBuffer();

            variableFileContent.appendLine(newValue.getTypeName());
            variableFileContent.append(newValue.toInstructionForm());

            variableVariable.write(variableFileContent.extractContent());
            variableVariable.close();
        } catch(IOException exception) {
            throw new VariableFileValueSettingFailedException(this, exception, newValue);
        }
    }

    public <T extends Entity> void change(Function<T, T> changer) {
        set(changer.apply(get()));
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
