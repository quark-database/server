package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.RecordsFile;

public class RecordsFileWritingFailedException extends DatabaseFileException {
    public RecordsFileWritingFailedException(RecordsFile recordsFile, Throwable causedBy) {
        super("Writing to the records file of the table %s in database %s is failed, because of %s: %s".formatted(
                recordsFile.getTable().getName(),
                recordsFile.getTable().getDatabase().getName(),
                causedBy.getClass().getSimpleName(),
                causedBy.getMessage()
            )
        );

        initCause(causedBy);
    }
}
