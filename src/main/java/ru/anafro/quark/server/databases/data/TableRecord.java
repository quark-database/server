package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.entities.ListEntity;

import java.util.ArrayList;

public class TableRecord {
    private final ArrayList<String> types;
    private final UntypedTableRecord untypedTableRecord;

    public TableRecord(ListEntity entities) {
        this.types = new ArrayList<>();
        var entitiesAsStrings = new ArrayList<String>();

        for(var entity : entities) {
            types.add(entity.getType());
            entitiesAsStrings.add(entity.getValueAsString());
        }

        this.untypedTableRecord = new UntypedTableRecord(entitiesAsStrings);
    }

    public TableRecord(ArrayList<String> types, UntypedTableRecord untypedTableRecord) {
        this.types = types;
        this.untypedTableRecord = untypedTableRecord;
    }

    public UntypedTableRecord getUntypedTableRecord() {
        return untypedTableRecord;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
}
