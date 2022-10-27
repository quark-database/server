package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.entities.Entity;

public class TableRecordFinder implements RecordLambda<Boolean> {
    private final String columnName;
    private final Entity findingValue;

    public TableRecordFinder(String columnName, Entity findingValue) {
        this.columnName = columnName;
        this.findingValue = findingValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public Entity getFindingValue() {
        return findingValue;
    }

    @Override
    public Boolean apply(TableRecord record) {
        return record.getField(columnName).getValue().getValue().equals(findingValue.getValue());
    }
}


