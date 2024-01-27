package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableVariable;

public class VariableFileWrongLinesCountException extends DatabaseFileException {
    public VariableFileWrongLinesCountException(TableVariable tableVariable, int countOfLinesInFile) {
        super("Variable %s of table %s in the database %s cannot be read, amount of lines inside the variable's file is wrong (2 expected, but the file contains %d)".formatted(
                        tableVariable.getName(),
                        tableVariable.getTable().getName(),
                        tableVariable.getTable().getDatabase().getName(),
                        countOfLinesInFile
                )
        );
    }
}
