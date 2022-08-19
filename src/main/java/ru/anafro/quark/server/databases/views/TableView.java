package ru.anafro.quark.server.databases.views;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public record TableView(TableViewHeader header, List<TableViewRow> rows) implements Iterable<TableViewRow> {
    private static final String VERTICAL_SEPARATOR = "|", HORIZONTAL_SEPARATOR = "-", SEPARATOR_CROSSING = "+";
    private static final int CELL_PADDING = 1;

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

    public boolean isEmpty() {
        return header.columnNames().length == 0;
    }

    @Override
    public String toString() {
        TextBuffer result = new TextBuffer();
        var maxColumnWidths = getMaxColumnWidths();
        var horizontalSeparator = makeHorizontalSeparator(maxColumnWidths);

        result.appendLine(horizontalSeparator);
        for(var columnIndex = 0; columnIndex < header.columnNames().length; columnIndex++) {
            result.append(VERTICAL_SEPARATOR);
            result.append(" ".repeat(CELL_PADDING));
            result.append(" ".repeat((int) Math.ceil((double) (maxColumnWidths[columnIndex] - header.columnNames()[columnIndex].length()) / 2)));
            result.append(header.columnNames()[columnIndex]);
            result.append(" ".repeat((int) Math.floor((double) (maxColumnWidths[columnIndex] - header.columnNames()[columnIndex].length()) / 2)));
            result.append(" ".repeat(CELL_PADDING));
        }

        result.appendLine(VERTICAL_SEPARATOR);
        result.appendLine(horizontalSeparator);

        for(var row : rows) {
            for (var columnIndex = 0; columnIndex < header.columnNames().length; columnIndex++) {
                result.append(VERTICAL_SEPARATOR);
                result.append(" ".repeat(CELL_PADDING));
                result.append(row.cells()[columnIndex]);
                result.append(" ".repeat(maxColumnWidths[columnIndex] - row.cells()[columnIndex].length()));
                result.append(" ".repeat(CELL_PADDING));
            }

            result.appendLine(VERTICAL_SEPARATOR);
            result.appendLine(horizontalSeparator);
        }

        return result.extractContent();
    }

    private int getMaxColumnWidth(int columnIndex) {
        int maxColumnWidth = header.columnNames()[columnIndex].length();

        for(var row : rows) {
            maxColumnWidth = Math.max(maxColumnWidth, row.cells()[columnIndex].length());
        }

        return maxColumnWidth;
    }

    private int[] getMaxColumnWidths() {
        int[] maxWidths = new int[header.columnNames().length];

        for(int columnIndex = 0; columnIndex < header.columnNames().length; columnIndex++) {
            maxWidths[columnIndex] = getMaxColumnWidth(columnIndex);
        }

        return maxWidths;
    }

    private int tableWidth(int[] maxColumnWidths) {
        return IntStream.of(maxColumnWidths).sum() + (CELL_PADDING * 2 + 1) * header.columnNames().length + 1;
    }

    private String makeHorizontalSeparator(int[] maxColumnWidths) {
        return HORIZONTAL_SEPARATOR.repeat(tableWidth(maxColumnWidths));
    }
}
