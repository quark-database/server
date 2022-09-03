package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.Entity;

import java.util.stream.Collectors;

public class RecordTypeMismatchesTableHeaderException extends DatabaseException {
    public RecordTypeMismatchesTableHeaderException(Table table, ColumnDescription column, Entity value) {
        super("Table with columns %s cannot contain value %s with type %s in the column %s with type %s.".formatted(
                table.getHeader().getColumns().stream().map(headerColumn -> headerColumn.getName() + " is " + headerColumn.getType().getName()).collect(Collectors.joining(", ")),
                value.toInstructionForm(),
                value.getExactTypeName(),
                column.getName(),
                column.getType().getName()
        ));
    }
}
