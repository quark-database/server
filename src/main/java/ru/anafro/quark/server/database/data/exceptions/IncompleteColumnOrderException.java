package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.List;

public class IncompleteColumnOrderException extends DatabaseException {
    public IncompleteColumnOrderException(Table table, List<String> order, String missingColumn) {
        super(STR."The column \{Lists.join(order, ", ")} order must contain '\{missingColumn}' existing in the table '\{table.getName()}'");
    }
}
