package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class RecordFieldMissingException extends DatabaseException {
    public RecordFieldMissingException(TableRecord record, ColumnDescription column, Table table) {
        super("A record %s cannot be used in the table %s, because it's missing a field %s, but the table has a column with this name.".formatted(record.toTableLine(), table.getName(), column));
    }
}
