package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class RecordColumnsDisorderedException extends DatabaseException {
    public RecordColumnsDisorderedException(TableRecord record, ColumnDescription column, Table table) {
        super("A record %s has a field named %s, which is present in the table %s, but it's placed in a wrong order.".formatted(record.toTableLine(), column.name(), table.getName()));
    }
}
