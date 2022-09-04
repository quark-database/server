package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.exceptions.WrongCompoundedTableNameException;
import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.data.files.RecordsFile;
import ru.anafro.quark.server.databases.data.files.VariableFolder;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.objects.Nulls;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Table {
    public static final String COMPOUNDED_TABLE_NAME_SEPARATOR = ".";
    private final String name;
    private final Database database;
    private final HeaderFile headerFile;
    private final RecordsFile recordsFile;
    private final VariableFolder variableFolder;

    public static Table byName(String compoundedName) {
        if(!compoundedName.contains(COMPOUNDED_TABLE_NAME_SEPARATOR)) {
            throw new WrongCompoundedTableNameException(compoundedName);
        }

        var compoundedNameParts = compoundedName.split(Pattern.quote(COMPOUNDED_TABLE_NAME_SEPARATOR), 2);
        return new Table(compoundedNameParts[0], compoundedNameParts[1]);
    }

    public Table(String databaseName, String tableName) {
        this.name = tableName;
        this.database = Database.byName(databaseName);
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


        for(var record : recordsFile) {
            Quark.logger().error(Nulls.evalOrDefault(record, TableRecord::toTableLine, "<null table record>"));

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
        }

        return selectedRecords;
    }

    public void changeRecords(TableRecordChanger changer, TableRecordSelector selector) {
        recordsFile.forEach(record -> {
            if(selector.shouldBeSelected(record)) {
                // TODO change the record?
            }
        });
    }

    public TableViewHeader createTableViewHeader() {
        return new TableViewHeader(headerFile.getColumns().stream().map(ColumnDescription::getName).toArray(String[]::new));
    }
}
