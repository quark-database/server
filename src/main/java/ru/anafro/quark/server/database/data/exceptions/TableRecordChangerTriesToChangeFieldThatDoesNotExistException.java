package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class TableRecordChangerTriesToChangeFieldThatDoesNotExistException extends DatabaseException {
    public TableRecordChangerTriesToChangeFieldThatDoesNotExistException(Table table, TableRecordChanger changer) {
        super(STR."""
            A changer '\{changer.lambda()}' is trying to change a record's column '\{changer.column()}'\s
            of the table '\{table.getName()}' with columns '\{table.getHeader()}', but this column does not exist.
        """);
    }
}
