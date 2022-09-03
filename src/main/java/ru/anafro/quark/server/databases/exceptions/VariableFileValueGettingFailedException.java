package ru.anafro.quark.server.databases.exceptions;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.databases.data.files.VariableFile;

import java.io.IOException;

public class VariableFileValueGettingFailedException extends DatabaseFileException {
    public VariableFileValueGettingFailedException(VariableFile variableFile, IOException causedBy) {
        super("Variable %s of table %s in the database %s cannot be read, because of %s".formatted(
                variableFile.getName(),
                variableFile.getTable().getName(),
                variableFile.getTable().getDatabase().getName(),
                causedBy.toString()
            )
        );
    }
}
