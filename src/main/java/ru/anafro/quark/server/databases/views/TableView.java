package ru.anafro.quark.server.databases.views;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public record TableView(TableViewHeader header, List<TableViewRow> rows) implements Iterable<TableViewRow> {

    public static TableView empty() {
        return new TableView(new TableViewHeader(), List.of( /* nothing */ ));
    }

    public JSONObject toJson() {
        JSONObject jsonTableView = new JSONObject();
        jsonTableView.put("header", new JSONArray(List.of(header.columnNames())));

        JSONArray jsonTableViewRows = new JSONArray();

        for(var row : rows) {
            jsonTableViewRows.put(new JSONArray(row.cells()));
        }

        return jsonTableView;
    }

    @Override
    public Iterator<TableViewRow> iterator() {
        return rows.iterator();
    }
}
