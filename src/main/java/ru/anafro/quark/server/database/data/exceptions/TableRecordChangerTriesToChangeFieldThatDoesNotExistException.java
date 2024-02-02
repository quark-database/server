package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class TableRecordChangerTriesToChangeFieldThatDoesNotExistException extends DatabaseException {
    public TableRecordChangerTriesToChangeFieldThatDoesNotExistException(TableRecordChanger changer) {
        super(STR."""
            A changer '\{changer.lambda()}' is trying to change a record's column '\{changer.column()}', but this column does not exist.
        """);
    }
}
