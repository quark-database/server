package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.Entity;

import java.util.stream.Collectors;

public class TableRecordChangerWrongTypeException extends DatabaseException {
    public TableRecordChangerWrongTypeException(Table table, TableRecordChanger changer, Entity result) {
        super("A changer '%s' is trying to change a record's column '%s' of the table '%s' with columns '%s', but types of the column and the result of the changer are mismatching - the column has type %s, but an lambda inside the changer returned a type %s.".formatted(
                changer.lambda(),
                changer.column(),
                table.getName(),
                table.getHeader().getColumns().stream().map(headerColumn -> STR."\{headerColumn.name()} is \{headerColumn.type().getName()}").collect(Collectors.joining(", ")),
                result.getType().getName(),
                table.getHeader().getColumn(changer.column()).map(ColumnDescription::getTypeName).orElse("(column is unset)")
        ));
    }
}
