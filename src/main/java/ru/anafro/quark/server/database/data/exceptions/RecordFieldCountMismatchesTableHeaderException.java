package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class RecordFieldCountMismatchesTableHeaderException extends DatabaseException {
    public RecordFieldCountMismatchesTableHeaderException(Table table, int fieldsInRecord) {
        super("Table with %d columns cannot contain a record with %d fields".formatted(table.getHeader().getColumns().size(), fieldsInRecord));
    }
}
