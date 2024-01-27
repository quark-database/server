package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.Entity;

import java.util.stream.Collectors;

public class RecordTypeMismatchesTableHeaderException extends DatabaseException {
    public RecordTypeMismatchesTableHeaderException(Table table, ColumnDescription column, Entity value) {
        super("Table with columns %s cannot contain value %s with type %s in the column %s with type %s.".formatted(
                table.getHeader().getColumns().stream().map(headerColumn -> STR."\{headerColumn.name()} is \{headerColumn.type().getName()}").collect(Collectors.joining(", ")),
                value.toInstructionForm(),
                value.getExactTypeName(),
                column.name(),
                column.type().getName()
        ));
    }
}
