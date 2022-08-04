package ru.anafro.quark.server.databases.views;

import java.util.ArrayList;

public class TableViewRecorder {
    private final TableViewHeader header;
    private final ArrayList<TableViewRow> rows = new ArrayList<>();

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
