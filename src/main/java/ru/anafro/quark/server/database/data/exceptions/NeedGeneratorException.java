package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class NeedGeneratorException extends DatabaseException {
    public NeedGeneratorException(ColumnDescription column) {
        super(STR."Since column '\{column.name()}' has no generating modifier, you need to provide a generator.");
    }
}
