package ru.anafro.quark.server.database.views;

import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;

public class TableViewRecorder {
    private final TableViewHeader header;
    private final ArrayList<TableViewRow> rows = Lists.empty();

    public TableViewRecorder(TableViewHeader header) {
        this.header = header;
    }

    public void appendRow(TableViewRow row) {
        rows.add(row);
    }

    public TableView collectView() {
        return new TableView(header, rows);
    }
}
