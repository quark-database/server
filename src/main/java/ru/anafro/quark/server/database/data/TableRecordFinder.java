package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.language.entities.Entity;

public record TableRecordFinder(String columnName, Entity findingValue) implements RecordLambda<Boolean> {

    public static TableRecordFinder finder(String columnName, Object findingValue) {
        return new TableRecordFinder(columnName, Entity.wrap(findingValue));
    }

    @Override
    public Boolean apply(TableRecord record) {
        return record.getField(columnName).getEntity().getValue().equals(findingValue.getValue());
    }
}


