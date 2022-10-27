package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.databases.data.files.HeaderFile;
import ru.anafro.quark.server.databases.data.files.RecordsFile;
import ru.anafro.quark.server.databases.data.files.VariableFolder;
import ru.anafro.quark.server.databases.data.structures.LinearRecordCollection;
import ru.anafro.quark.server.databases.data.structures.RecordCollection;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.databases.ql.entities.ColumnEntity;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.views.TableViewHeader;
import ru.anafro.quark.server.files.Databases;
import ru.anafro.quark.server.files.FileSystem;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Table {
    private final String name;
    private final Database database;
    private final HeaderFile headerFile;
    private final RecordsFile recordsFile;
    private final VariableFolder variableFolder;

    public static Table byName(String compoundedName) {
        return Table.byName(new CompoundedTableName(compoundedName));
    }

    public static Table byName(CompoundedTableName compoundedName) {
        return new Table(compoundedName.getDatabaseName(), compoundedName.getTableName());
    }

    public static boolean exists(String compoundedName) {
        var name = new CompoundedTableName(compoundedName);

        if(!Database.exists(name.getDatabaseName())) {
            return false;
        }

        var tableDirectory = new File(Path.of(Databases.get(name.getDatabaseName()), name.getTableName()).toUri());
        return tableDirectory.exists() && tableDirectory.isDirectory();
    }

    public static boolean exists(CompoundedTableName tableName) {
        return exists(tableName.toCompoundedString());
    }

    public Table(String databaseName, String tableName) {
        this.name = tableName;
        this.database = Database.byName(databaseName);
        this.headerFile = new HeaderFile(this);
        this.recordsFile = new RecordsFile(this);
        this.variableFolder = new VariableFolder(this);
    }

    public static Table create(CompoundedTableName name, List<ColumnDescription> columns, List<ColumnModifierEntity> modifiers) {
        try {
            if(exists(name.toCompoundedString())) {
                throw new DatabaseFileException("Table '%s' already exists".formatted(name.toCompoundedString()));
            }

            var tableFolder = new File(Path.of(Databases.get(name.getDatabaseName()), name.getTableName()).toUri());

            Files.createDirectory(tableFolder.toPath());

            Files.createFile(Path.of(tableFolder.getPath(), RecordsFile.NAME));
            Files.createFile(Path.of(tableFolder.getPath(), HeaderFile.NAME));
            Files.createDirectory(Path.of(tableFolder.getPath(), VariableFolder.NAME));

            var lines = new TextBuffer();

            lines.appendLine(ListEntity.of(columns.stream().map(ColumnEntity::new).toList()).toInstructionForm());
            lines.appendLine(ListEntity.of(modifiers).toInstructionForm());

            Files.writeString(Path.of(tableFolder.getPath(), HeaderFile.NAME), lines);

            return byName(name);
        } catch (IOException exception) {
            throw new DatabaseFileException("Table cannot be created, because of %s: %s".formatted(exception.getClass().getSimpleName(), exception.getMessage()));
        }
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
//        headerFile.runBeforeRecordInsertionActionOfModifiers(record);
        recordsFile.insert(record);
    }

    public RecordCollection loadRecords(RecordCollectionResolver resolver) {
        RecordCollection collection = resolver.createEmptyCollection();
        collection.addAll(recordsFile);

        return collection;
    }

    public RecordCollection loadRecords() {
        return loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.NO_FURTHER_MANIPULATIONS));
    }

    public RecordCollection select(TableRecordSelector selector, RecordIterationLimiter limiter) {
        // TODO: Change the collection resolver case (pull it to the higher level?)
        var allRecords = loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.SELECTOR_IS_TOO_COMPLEX));

        return allRecords.select(selector, limiter);
    }

    public void changeRecords(TableRecordChanger changer, ExpressionTableRecordSelector selector) {
        var allRecords = loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.JUST_SELECT_EVERYTHING));

        allRecords.forEach(record -> {
            if(selector.shouldBeSelected(record)) {
                changer.change(record);
            }
        });

        recordsFile.save(allRecords);
    }

    public TableViewHeader createTableViewHeader() {
        return new TableViewHeader(headerFile.getColumns().stream().map(ColumnDescription::getName).toArray(String[]::new));
    }

    public void delete() {
//        boolean ignored = new File(Path.of(database.getFolder().getPath(), name).toUri()).delete();
        FileSystem.deleteIfExists(Path.of(database.getFolder().getPath(), name).toString());
    }

    public void clear() {
        getRecords().save(new LinearRecordCollection());
    }
}
