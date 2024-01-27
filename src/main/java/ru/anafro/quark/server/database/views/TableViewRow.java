package ru.anafro.quark.server.database.views;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static ru.anafro.quark.server.utils.arrays.Arrays.map;

public final class TableViewRow implements Iterable<String> {
    private final String[] cells;

    public TableViewRow(Object[] cells) {
        this.cells = map(String.class, cells, Objects::toString);
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(cells).iterator();
    }

    public String[] cells() {
        return cells;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TableViewRow) obj;
        return Arrays.equals(this.cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) cells);
    }

    @Override
    public String toString() {
        return STR."TableViewRow[cells=\{cells}\{']'}";
    }
}
