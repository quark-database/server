package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;

public class VariableAlreadyExistsException extends DatabaseFileException {
    public VariableAlreadyExistsException(Table table, String variableName) {
        super("A variable '%s' already exists in the table '%s'.".formatted(
                variableName,
                table.getName()
        ));
    }
}
