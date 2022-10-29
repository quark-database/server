package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.Table;

public class VariableNotFoundException extends DatabaseFileException {
    public VariableNotFoundException(Table table, String variableName) {
        super("A variable '%s' does not exist inside the table '%s'.".formatted(
                variableName,
                table.getName()
        ));
    }
}
