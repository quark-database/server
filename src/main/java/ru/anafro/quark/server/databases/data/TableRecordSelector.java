package ru.anafro.quark.server.databases.data;

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
}
