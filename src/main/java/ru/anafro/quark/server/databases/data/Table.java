package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.data.files.RecordsFile;
import ru.anafro.quark.server.databases.data.files.VariableFolder;
import ru.anafro.quark.server.utils.containers.Lists;

import java.util.ArrayList;

public class Table {
    private final String name;
    private final Database database;
    private final HeaderFile headerFile;
    private final RecordsFile recordsFile;
    private final VariableFolder variableFolder;

    public Table(String name, Database database) {
        this.name = name;
        this.database = database;
        this.headerFile = new HeaderFile(this);
        this.recordsFile = new RecordsFile(this);
        this.variableFolder = new VariableFolder(this);
    }

    public String getName() {
        return name;
    }

    public Database getDatabase() {
        return database;
    }

    public HeaderFile getHeader() {
        return headerFile;
    }

    public RecordsFile getRecords() {
        return recordsFile;
    }

    public VariableFolder getVariableFolder() {
        return variableFolder;
    }

    public void insert(TableRecord record) {
        headerFile.requireValidity(record);
        headerFile.runBeforeRecordInsertionActionOfModifiers(record);
        recordsFile.insert(record);
    }

    public ArrayList<TableRecord> select(TableRecordSelector selector, long skip, long limit) {
        if(limit == 0) {
            return Lists.empty();
        }

        var selectedRecords = Lists.<TableRecord>empty();

        final long[] recordsLeftToSkip = { skip };
        final long[] recordsLeftToSelect = { limit };

        recordsFile.forEach(record -> {
            if(selector.shouldBeSelected(record)) {
                if(recordsLeftToSelect[0] > 0) {
                    if(recordsLeftToSkip[0] > 0) {
                        recordsLeftToSkip[0]--;
                    } else {
                        selectedRecords.add(record);
                        recordsLeftToSelect[0]--;
                    }
                }
            }
        });

        return selectedRecords;
    }

    public void changeRecords(TableRecordChanger changer, TableRecordSelector selector) {
        recordsFile.forEach(record -> {
            if(selector.shouldBeSelected(record)) {
                // TODO change the record?
            }
        });
    }
}
