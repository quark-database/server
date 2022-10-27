package ru.anafro.quark.server.console;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.exceptions.CommandScriptFileCannotBeReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommandScriptFile implements Runnable {
    public static final String FOLDER = "Commands";
    public static final String EXTENSION = ".qcommandscript";
    private final String path;

    public CommandScriptFile(String name) {
        this.path = Path.of(FOLDER, name + EXTENSION).toAbsolutePath().toString();
    }

    public String getPath() {
        return path;
    }

    public List<String> read() {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException exception) {
            throw new CommandScriptFileCannotBeReadException(this, exception);
        }
    }

    @Override
    public void run() {
        var commands = read();

        for(var command : commands) {
            Quark.runCommand(command);
        }
    }
}
