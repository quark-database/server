package ru.anafro.quark.server.databases.exceptions;

import ru.anafro.quark.server.databases.views.TableViewHeader;

public class CellCountInRowMismatchesViewHeaderException extends DatabaseException {
    public CellCountInRowMismatchesViewHeaderException(int cellsInRow, TableViewHeader header) {
        super("Cannot produce a new row with %d values, because the table header contains %s columns, exactly %d".formatted(cellsInRow, header.columnNames().length > cellsInRow ? "more" : "less", header.columnNames().length));
    }
}
