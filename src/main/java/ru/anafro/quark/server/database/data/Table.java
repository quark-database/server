package ru.anafro.quark.server.database.data;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.exceptions.DatabaseFileException;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.database.data.files.TableHeader;
import ru.anafro.quark.server.database.data.files.TableRecords;
import ru.anafro.quark.server.database.data.files.TableVariable;
import ru.anafro.quark.server.database.data.files.VariableDirectory;
import ru.anafro.quark.server.database.data.structures.LinearRecordCollection;
import ru.anafro.quark.server.database.data.structures.RecordCollection;
import ru.anafro.quark.server.database.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.language.entities.ColumnEntity;
import ru.anafro.quark.server.language.entities.ListEntity;
import ru.anafro.quark.server.database.views.TableViewHeader;
import ru.anafro.quark.server.files.DatabasesDirectory;
import ru.anafro.quark.server.utils.files.Directory;
import ru.anafro.quark.server.utils.files.exceptions.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Table implements Iterable<TableRecord> {
    private static final DatabasesDirectory databasesDirectory = DatabasesDirectory.getInstance();
    private final String name;
    private final Database database;
    private final Directory directory;
    private final TableHeader header;
    private final TableRecords records;
    private final VariableDirectory variableDirectory;

    protected Table(String databaseName, String tableName) {
        var fullName = new TableName(databaseName, tableName);

        if (doesntExist(fullName)) {
            throw new TableNotFoundException(fullName);
        }

        this.name = tableName;
        this.database = Database.byName(databaseName);
        this.directory = databasesDirectory.getTableDirectory(database.getName(), this.name);
        this.header = new TableHeader(this);
        this.records = new TableRecords(this);
        this.variableDirectory = new VariableDirectory(this);
    }

    public static Table byName(String databaseName, String tableName) {
        return new Table(databaseName, tableName);
    }

    public static Table byName(String compoundedName) {
        return Table.byName(new TableName(compoundedName));
    }

    public static Table byName(TableName compoundedName) {
        return Table.byName(compoundedName.getDatabaseName(), compoundedName.getTableName());
    }

    public static boolean exists(TableName compoundedTableName) {
        var databaseName = compoundedTableName.getDatabaseName();
        var tableName = compoundedTableName.getTableName();

        return databasesDirectory.hasDirectory(databaseName, tableName);
    }

    public static boolean exists(String compoundedName) {
        return exists(new TableName(compoundedName));
    }

    public static boolean doesntExist(TableName compoundedName) {
        return !exists(compoundedName);
    }

    public static boolean doesntExist(String compoundedName) {
        return !exists(compoundedName);
    }

    public static void ensureExists(String compoundedName) {
        if (!exists(compoundedName)) {
            throw new TableNotFoundException(compoundedName);
        }
    }

    public static void deleteIfExists(TableName tableName) {
        if (doesntExist(tableName)) {
            return;
        }

        byName(tableName).delete();
    }

    public static Table create(TableName tableName, List<ColumnDescription> columns) {
        Database.createIfDoesntExist(tableName.getDatabaseName());

        if (exists(tableName)) {
            throw new DatabaseFileException(STR."Table '\{tableName}' already exists.");
        }

        var databasesDirectory = DatabasesDirectory.getInstance();
        var tableDirectory = databasesDirectory.getTableDirectory(tableName);

        tableDirectory.createDirectory(VariableDirectory.NAME);
        tableDirectory.createFile(TableRecords.NAME);
        tableDirectory.createFile(TableHeader.NAME, STR."""
                \{ListEntity.of(columns.stream().map(ColumnEntity::new).toList())}
                """);

        return byName(tableName);
    }

    public String getName() {
        return name;
    }

    public Database getDatabase() {
        return database;
    }

    public TableHeader getHeader() {
        return header;
    }

    public TableRecords getRecords() {  // TODO: Encapsulate?
        return records;
    }

    public VariableDirectory getVariableDirectory() {
        return variableDirectory;
    }

    public void rename(String newName) {
        try {
            var databaseDirectory = database.getDirectory();

            Files.move(databaseDirectory.getFilePath(name), databaseDirectory.getFilePath(newName));
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }
    }

    public void insert(Object... recordEntities) {
        insert(new TableRecord(this, ListEntity.of(recordEntities)));
    }

    public void insert(TableRecord record) {
        header.ensureRecordIsValid(record);
        records.insert(record);
    }

    public RecordCollection loadRecords(RecordCollectionResolver resolver) {
        RecordCollection collection = resolver.createEmptyCollection();
        collection.addAll(records);

        return collection;
    }

    public RecordCollection loadRecords() {
        return loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.NO_FURTHER_MANIPULATIONS));
    }

    public RecordCollection selectAll() {
        return loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.SELECTOR_IS_TOO_COMPLEX));
    }

    public RecordCollection select(TableRecordSelector selector, RecordIterationLimiter limiter) {
        return selectAll().select(selector, limiter);
    }

    public TableViewHeader createViewHeader() {
        return new TableViewHeader(header.getColumns().stream().map(ColumnDescription::name).toArray(String[]::new));
    }

    public Directory getDirectory() {
        return directory;
    }

    public void delete() {
        getDirectory().delete();
    }

    public Table copy(TableName destinationName) {
        var databaseName = destinationName.getDatabaseName();
        var tableName = destinationName.getTableName();
        var database = Database.createIfDoesntExist(databaseName);
        var databaseDirectory = database.getDirectory();
        var destinationPath = databaseDirectory.getFilePath(tableName);

        directory.copy(destinationPath);

        return byName(destinationName);
    }

    public void clear() {
        records.save(new LinearRecordCollection());
    }

    public TableVariable getVariable(String name) {
        return variableDirectory.getVariable(name);
    }

    public Optional<ColumnDescription> getColumn(String columnName) {
        return header.getColumn(columnName);
    }

    public boolean hasColumn(String columnName) {
        return getColumn(columnName).isPresent();
    }

    public List<ColumnDescription> getColumns() {
        return header.getColumns();
    }

    @NotNull
    @Override
    public Iterator<TableRecord> iterator() {
        return selectAll().iterator();
    }

    public void store(RecordCollection collection) {
        records.save(collection);
    }

    public void saveHeader() {
        header.save();
    }

    public boolean canNotUse(TableRecordChanger changer) {
        return header.doesntHaveColumn(changer.column());
    }
}
