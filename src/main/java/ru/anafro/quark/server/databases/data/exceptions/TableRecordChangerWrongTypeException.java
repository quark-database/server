package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.Entity;

import java.util.stream.Collectors;

public class TableRecordChangerWrongTypeException extends DatabaseException {
    public TableRecordChangerWrongTypeException(Table table, TableRecordChanger changer, Entity result) {
        super("A changer '%s' is trying to change a record's column '%s' of the table '%s' with columns '%s', but types of the column and the result of the changer are mismatching - the column has type %s, but an expression inside the changer returned a type %s.".formatted(
                changer.expression(),
                changer.column(),
                table.getName(),
                table.getHeader().getColumns().stream().map(headerColumn -> headerColumn.getName() + " is " + headerColumn.getType().getName()).collect(Collectors.joining(", ")),
                result.getType().getName(),
                table.getHeader().getColumn(changer.column()).getType().getName()
        ));
    }
}
