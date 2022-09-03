package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.ql.types.EntityType;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.patterns.Builder;

import java.util.ArrayList;

public class TableRecordBuilder implements Builder<TableRecord> {
    private Table table;
    private final ArrayList<RecordField> fields = Lists.empty();

    public TableRecordBuilder table(Table table) {
        this.table = table;
        return this;
    }

    public TableRecordBuilder fieldsFrom(UntypedTableRecord untypedRecord, HeaderFile headerFile) {
        for(int index = 0; index < untypedRecord.size(); index++) {
            this.field(headerFile.columnAt(index).getName(), untypedRecord.valueAt(index), headerFile.columnAt(index).getType());
        }

        return this;
    }

    public TableRecordBuilder field(String fieldName, String value, EntityType type) {
        fields.add(new RecordField(fieldName, type.makeEntity(value)));
        return this;
    }

    @Override
    public TableRecord build() {
        return null;
    }
}
