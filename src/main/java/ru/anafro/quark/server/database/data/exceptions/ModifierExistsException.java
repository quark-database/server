package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.ColumnModifierEntity;

public class ModifierExistsException extends DatabaseException {
    public ModifierExistsException(ColumnDescription column, ColumnModifierEntity modifier) {
        super(STR."The modifier \{modifier} already exists in the column \{column.name()}");
    }
}
