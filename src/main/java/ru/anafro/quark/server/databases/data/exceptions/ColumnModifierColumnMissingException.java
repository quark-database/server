package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;

public class ColumnModifierColumnMissingException extends DatabaseException {
    public ColumnModifierColumnMissingException(TableRecord record, ColumnModifierEntity modifierEntity, Table table) {
        super("A record %s is being tried to be validated by a modifier %s in the table %s, but validating column %s is missing in the record.".formatted(record.toTableLine(), modifierEntity.getModifier().getName(), table.getName(), modifierEntity.getColumnName()));
    }
}
