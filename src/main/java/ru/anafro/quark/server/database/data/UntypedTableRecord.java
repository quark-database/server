package ru.anafro.quark.server.database.data;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.files.TableHeader;
import ru.anafro.quark.server.database.data.parser.RecordCharacterEscapeService;
import ru.anafro.quark.server.database.data.parser.RecordParser;
import ru.anafro.quark.server.utils.collections.Lists;

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

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public void add(String recordCell) {
        values.add(recordCell);
    }

    @Override
    public String toString() {
        return Lists.join(values, value -> new RecordCharacterEscapeService().wrapEscapableCharacters(value));
    }

    public TableRecord applyTypesFrom(TableHeader header) {
        ArrayList<RecordField> fields = Lists.empty();

        for (int index = 0; index < size(); index++) {
            var column = header.columnAt(index);
            var value = column.type().makeEntity(valueAt(index));

            fields.add(new RecordField(column.name(), value));
        }

        return new TableRecord(header.getTable(), fields);
    }

    public String valueAt(int index) {
        return values.get(index);
    }
}
