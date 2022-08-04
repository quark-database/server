package ru.anafro.quark.server.databases.views;

import java.util.Arrays;
import java.util.Iterator;

public record TableViewRow(String... cells) implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(cells).iterator();
    }
}
