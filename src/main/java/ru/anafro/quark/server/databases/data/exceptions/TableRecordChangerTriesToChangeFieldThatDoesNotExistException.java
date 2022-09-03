package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;

import java.util.stream.Collectors;

public class TableRecordChangerTriesToChangeFieldThatDoesNotExistException extends DatabaseException {
    public TableRecordChangerTriesToChangeFieldThatDoesNotExistException(Table table, TableRecord record, TableRecordChanger changer) {
        super("A changer '%s' is trying to change a record's column '%s' of the table '%s' with columns '%s', but this column does not exist.".formatted(
                changer.expression(),
                changer.column(),
                table.getName(),
                table.getHeader().getColumns().stream().map(headerColumn -> headerColumn.getName() + " is " + headerColumn.getType().getName()).collect(Collectors.joining(", "))
        ));
    }
}
