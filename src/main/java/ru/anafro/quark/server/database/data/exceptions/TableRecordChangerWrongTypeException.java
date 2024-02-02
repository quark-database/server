package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.Entity;

public class TableRecordChangerWrongTypeException extends DatabaseException {
    public TableRecordChangerWrongTypeException(TableRecordChanger changer, Entity result) {
        super("A changer '%s' is trying to change a record's column '%s', but returned %s, which mismatches the column type.".formatted(
                changer.lambda(),
                changer.column(),
                result.getExactTypeName()
        ));
    }
}
