package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class RecordFieldMissingException extends DatabaseException {
    public RecordFieldMissingException(TableRecord record, ColumnDescription column, Table table) {
        super("A record %s cannot be used in the table %s, because it's missing a field %s with type %s, but the table has a column with this name.".formatted(record.toTableLine(), table.getName(), column.name(), column.type().getName()));
    }
}
