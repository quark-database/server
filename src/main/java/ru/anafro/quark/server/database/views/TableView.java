package ru.anafro.quark.server.database.views;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.strings.Strings;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static ru.anafro.quark.server.utils.collections.Collections.list;
import static ru.anafro.quark.server.utils.strings.Strings.spaces;

public record TableView(TableViewHeader header, List<TableViewRow> rows) implements Iterable<TableViewRow> {
    private static final String verticalBorder = "│";
    private static final String HORIZONTAL_SEPARATOR = "─";
    private static final int CELL_PADDING = 4;

    public static TableView empty() {
        return new TableView(new TableViewHeader(), Lists.empty());
    }

    public JSONObject toJson() {
        JSONObject jsonTableView = new JSONObject();
        jsonTableView.put("header", new JSONArray(List.of(header.columnNames())));

        JSONArray jsonTableViewRows = new JSONArray();

        for (var row : rows) {
            jsonTableViewRows.put(new JSONArray(row.cells()));
        }

        jsonTableView.put("records", jsonTableViewRows);
        return jsonTableView;
    }

    @NotNull
    @Override
    public Iterator<TableViewRow> iterator() {
        return rows.iterator();
    }

    public boolean isEmpty() {
        return header.columnNames().length == 0;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "< an empty table view >";
        }

        var result = new TextBuffer();
        var columnNames = header.columnNames();
        var maxColumnWidths = getMaxColumnWidths();
        var cellPadding = spaces(CELL_PADDING);

        result.appendLine(makeTopHorizontalSeparator(maxColumnWidths));
        for (var columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            var columnWidth = maxColumnWidths[columnIndex];
            var columnName = columnNames[columnIndex];
            var selfPadding = columnWidth - columnName.length();
            var leftPadding = spaces(Math.ceilDiv(selfPadding, 2));
            var rightPadding = spaces(Math.floorDiv(selfPadding, 2));

            result.append(verticalBorder, cellPadding, leftPadding, STR."<indigo>\{columnName}</>", rightPadding, cellPadding);
        }

        result.appendLine(verticalBorder);

        if (rows.isEmpty()) {
            result.appendLine(makeHorizontalSeparator(maxColumnWidths, '├', "┴", '┤'));
            result.appendLine(verticalBorder, "<gray>", Strings.padCenter("(No cells)", tableWidth(maxColumnWidths) - 2), "</>", verticalBorder);
            result.appendLine("└", HORIZONTAL_SEPARATOR.repeat(tableWidth(maxColumnWidths) - 2), "┘");
        } else {
            result.appendLine(makeMiddleHorizontalSeparator(maxColumnWidths));
        }

        for (int i = 0; i < rows.size(); i++) {
            var row = rows.get(i);
            var cells = row.cells();

            for (var columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
                var cell = cells[columnIndex];
                var columnName = columnNames[columnIndex];
                var columnWidth = maxColumnWidths[columnIndex];
                var rightPadding = spaces(maxColumnWidths[columnIndex] - cell.length());

                result.append(verticalBorder, cellPadding, STR."<teal>\{cell}</>", rightPadding, cellPadding);
            }

            result.appendLine(verticalBorder);
            result.appendLine(i == rows.size() - 1 ? makeBottomHorizontalSeparator(maxColumnWidths) : makeMiddleHorizontalSeparator(maxColumnWidths));
        }

        return result.extractContent();
    }

    private int getMaxColumnWidth(int columnIndex) {
        int maxColumnWidth = header.columnNames()[columnIndex].length();

        for (var row : rows) {
            maxColumnWidth = Math.max(maxColumnWidth, row.cells()[columnIndex].length());
        }

        return maxColumnWidth;
    }

    private int[] getMaxColumnWidths() {
        int[] maxWidths = new int[header.columnNames().length];

        for (int columnIndex = 0; columnIndex < header.columnNames().length; columnIndex++) {
            maxWidths[columnIndex] = getMaxColumnWidth(columnIndex);
        }

        return maxWidths;
    }

    private int tableWidth(int[] maxColumnWidths) {
        return IntStream.of(maxColumnWidths).sum() + (CELL_PADDING * 2 + 1) * header.columnNames().length + 1;
    }

    private String makeHorizontalSeparator(int[] maxColumnWidths, char leftBorder, String crossing, char rightBorder) {
        var horizontalSeparator = new TextBuffer();

        horizontalSeparator.append(leftBorder);
        horizontalSeparator.appendMany(list(maxColumnWidths), columnWidth -> HORIZONTAL_SEPARATOR.repeat(columnWidth + CELL_PADDING * 2), crossing);
        horizontalSeparator.append(rightBorder);

        return horizontalSeparator.getContent();
    }

    private String makeTopHorizontalSeparator(int[] maxColumnWidths) {
        return makeHorizontalSeparator(maxColumnWidths, '┌', "┬", '┐');
    }

    private String makeMiddleHorizontalSeparator(int[] maxColumnWidths) {
        return makeHorizontalSeparator(maxColumnWidths, '├', "┼", '┤');
    }

    private String makeBottomHorizontalSeparator(int[] maxColumnWidths) {
        return makeHorizontalSeparator(maxColumnWidths, '└', "┴", '┘');
    }
}
