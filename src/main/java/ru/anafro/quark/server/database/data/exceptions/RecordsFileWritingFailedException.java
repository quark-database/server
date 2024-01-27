package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableRecords;

public class RecordsFileWritingFailedException extends DatabaseFileException {
    public RecordsFileWritingFailedException(TableRecords tableRecords, Throwable causedBy) {
        super("Writing to the records file of the table %s in database %s is failed, because of %s: %s".formatted(
                        tableRecords.getTable().getName(),
                        tableRecords.getTable().getDatabase().getName(),
                        causedBy.getClass().getSimpleName(),
                        causedBy.getMessage()
                )
        );

        initCause(causedBy);
    }
}
