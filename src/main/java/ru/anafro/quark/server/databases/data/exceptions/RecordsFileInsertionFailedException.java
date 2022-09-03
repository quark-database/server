package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.files.RecordsFile;

import java.io.IOException;

public class RecordsFileInsertionFailedException extends DatabaseFileException {
    public RecordsFileInsertionFailedException(RecordsFile recordsFile, TableRecord record, IOException causedBy) {
        super("Inserting a new record %s to the table %s is failed, because of %s.".formatted(record.toTableLine(), recordsFile.getTable(), causedBy.toString()));
        initCause(causedBy);
    }
}
