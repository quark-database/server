package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.files.TableRecords;

import java.io.IOException;

public class RecordsFileInsertionFailedException extends DatabaseFileException {
    public RecordsFileInsertionFailedException(TableRecords tableRecords, TableRecord record, IOException causedBy) {
        super("Inserting a new record %s to the table %s is failed, because of %s.".formatted(record.toTableLine(), tableRecords.getTable(), causedBy.toString()));
        initCause(causedBy);
    }
}
