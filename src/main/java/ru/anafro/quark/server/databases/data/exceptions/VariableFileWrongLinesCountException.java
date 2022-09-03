package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.VariableFile;

public class VariableFileWrongLinesCountException extends DatabaseFileException {
    public VariableFileWrongLinesCountException(VariableFile variableFile, int countOfLinesInFile) {
        super("Variable %s of table %s in the database %s cannot be read, amount of lines inside the variable's file is wrong (2 expected, but the file contains %d)".formatted(
                        variableFile.getName(),
                        variableFile.getTable().getName(),
                        variableFile.getTable().getDatabase().getName(),
                        countOfLinesInFile
                )
        );
    }
}
