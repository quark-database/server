package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.data.parser.RecordCharacterEscapeService;
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
        return Lists.joinPresentations(values, value -> new RecordCharacterEscapeService().wrapEscapableCharacters(value));
    }

    public TableRecord applyTypesFrom(HeaderFile header) {
        return new TableRecordBuilder().fieldsFrom(this, header).build();
    }

    public String valueAt(int index) {
        return values.get(index);
    }
}
