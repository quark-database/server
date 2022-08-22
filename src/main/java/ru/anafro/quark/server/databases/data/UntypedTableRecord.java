package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.parser.RecordParser;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;
import java.util.Iterator;

public record UntypedTableRecord(ArrayList<String> values) implements Iterable<String> {
    public static UntypedTableRecord fromString(String recordLine) {
        var parser = new RecordParser();
        parser.parse(recordLine);

        return parser.getRecord();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public void add(String recordCell) {
        values.add(recordCell);
    }

    @Override
    public String toString() {
        return Lists.join(values); // TODO: It seems like this can fail in some cases. Please, read this code carefully to ensure that this code is fine or rewrite it if it doesn't
    }
}
