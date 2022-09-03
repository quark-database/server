package ru.anafro.quark.server.databases.ql.entities.constructors.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class ColumnModifierNotFoundException extends DatabaseException {
    public ColumnModifierNotFoundException(String modifierName) {
        super("There is no column modifier registered with name %s.".formatted(modifierName));
    }
}
