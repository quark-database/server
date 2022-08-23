package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.changer.TableRecordSelector;
import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.data.files.RecordsFile;

public class Table {
    private final String name;
    private final Database database;
    private final HeaderFile headerFile;
    private final RecordsFile recordsFile;

    public Table(String name, Database database) {
        this.name = name;
        this.database = database;
        this.headerFile = new HeaderFile(this);
        this.recordsFile = new RecordsFile(this);
    }

    public String getName() {
        return name;
    }

    public Database getDatabase() {
        return database;
    }

    public HeaderFile getHeaderFile() {
        return headerFile;
    }

    public RecordsFile getRecordsFile() {
        return recordsFile;
    }

    public void insert(TableRecord record) {
        this.insert(record.getUntypedTableRecord());
    }

    public void insert(UntypedTableRecord record) {
        // TODO
    }

    public void select(TableRecordSelector selector, long skip, long limit) {

    }

    public void changeRecords(TableRecordSelector selector) {

    }
}
