package ru.anafro.quark.server.databases.views;

import org.json.JSONArray;
import ru.anafro.quark.server.databases.exceptions.CellCountInRowMismatchesViewHeaderException;

public record TableViewHeader(String... columnNames) {
    public TableViewRow produceRow(String... cells) {
        if(columnNames.length == cells.length) {
            return new TableViewRow(cells);
        }

        throw new CellCountInRowMismatchesViewHeaderException(cells.length, this);
    }

    public JSONArray toJson() {
        return new JSONArray(columnNames);
    }
}
