package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.ColumnModifierEntity;

public class ColumnModifierValidityCheckFailedException extends DatabaseException {
    public ColumnModifierValidityCheckFailedException(TableRecord record, Table table, ColumnModifierEntity modifierEntity) {
        super("A table modifier %s checked the validity of a record %s in the table %s, but the validation failed.".formatted(modifierEntity.getModifier().getName(), record.toTableLine(), table.getName()));
    }
}
