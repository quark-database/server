package ru.anafro.quark.server.database.views;

import java.util.Arrays;

public record TableViewHeader(String... columnNames) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableViewHeader that = (TableViewHeader) o;
        return Arrays.equals(columnNames, that.columnNames);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(columnNames);
    }
}
