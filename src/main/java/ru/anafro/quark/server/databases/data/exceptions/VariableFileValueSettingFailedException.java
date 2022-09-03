package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.VariableFile;
import ru.anafro.quark.server.databases.ql.entities.Entity;

import java.io.IOException;

public class VariableFileValueSettingFailedException extends DatabaseFileException {
    public VariableFileValueSettingFailedException(VariableFile variableFile, IOException causedBy, Entity newValue) {
        super("Variable %s of table %s in the database %s cannot be set with a value %s of type %s, because of %s".formatted(
                        variableFile.getName(),
                        variableFile.getTable().getName(),
                        variableFile.getTable().getDatabase().getName(),
                        newValue.toInstructionForm(),
                        newValue.getExactTypeName(),
                        causedBy.getMessage()
                )
        );

        initCause(causedBy);
    }
}
