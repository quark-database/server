package ru.anafro.quark.server.database.data;

public abstract class TableRecordSelector implements RecordLambda<Boolean> {
    private final String name;

    public TableRecordSelector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean is(String name) {
        return name.equals(this.name);
    }

    public boolean selects(TableRecord record) {
        return apply(record);
    }
}
