package ru.anafro.quark.server.database.data;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.exceptions.ColumnNotFoundException;
import ru.anafro.quark.server.database.data.exceptions.TableExistsException;
import ru.anafro.quark.server.database.data.exceptions.TableNotFoundException;
import ru.anafro.quark.server.database.data.files.TableHeader;
import ru.anafro.quark.server.database.data.files.TableRecords;
import ru.anafro.quark.server.database.data.files.TableVariable;
import ru.anafro.quark.server.database.data.files.VariableDirectory;
import ru.anafro.quark.server.database.data.structures.*;
import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.database.views.TableViewHeader;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.files.DatabasesDirectory;
import ru.anafro.quark.server.language.entities.*;
import ru.anafro.quark.server.language.exceptions.GeneratedValueMismatchesColumnTypeException;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.files.Directory;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.anafro.quark.server.database.data.Database.systemDatabase;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public class Table implements Iterable<TableRecord> {
    private static final DatabasesDirectory databasesDirectory = DatabasesDirectory.getInstance();
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

        this.database = Database.byName(databaseName);
        this.directory = databasesDirectory.getTableDirectory(database.getName(), tableName);
        this.header = new TableHeader(this);
        this.records = new TableRecords(this);
        this.variableDirectory = new VariableDirectory(this);
    }

    public static Table systemTable(String name) {
        return systemDatabase().table(name);
    }

    public static Table byName(String databaseName, String tableName) {
        return new Table(databaseName, tableName);
    }

    public static Table byName(String compoundedName) {
        return Table.byName(new TableName(compoundedName));
    }

    public static Table table(String name) {
        return byName(name);
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

    public static void deleteIfExists(String tableName) {
        if (doesntExist(tableName)) {
            return;
        }

        byName(tableName).delete();
    }

    public static Table create(String tableName, ColumnDescription... descriptions) {
        return create(new TableName(tableName), list(descriptions));
    }

    public static Table create(TableName tableName, List<ColumnDescription> columns) {
        Database.createIfDoesntExist(tableName.getDatabaseName());

        if (exists(tableName)) {
            throw new TableExistsException(tableName.toCompoundedString());
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

    public static Table create(String tableName, List<ColumnDescription> columns, List<RecordEntity> records) {
        return create(new TableName(tableName), columns, records);
    }

    public static Table create(TableName tableName, List<ColumnDescription> columns, List<RecordEntity> records) {
        var collection = new LinearRecordCollection();
        var table = create(tableName, columns);

        records.stream()
                .map(record -> new TableRecord(table.getHeader(), record.getValues()))
                .forEach(collection::add);

        table.store(collection);

        return table;
    }

    public String getName() {
        return directory.getName();
    }

    public Database getDatabase() {
        return database;
    }

    public TableHeader getHeader() {
        return header;
    }

    public List<TableVariable> variables() {
        return variableDirectory.all();
    }

    public void rename(String newName) {
        directory.rename(newName);
    }

    public void insert(Object... recordEntities) {
        insert(TableRecord.record(header, recordEntities));
    }

    public void insert(TableRecord record) {
        records.insert(record);
    }

    public RecordCollection loadRecords(RecordCollectionResolver resolver) {
        RecordCollection collection = resolver.createEmptyCollection();
        collection.addAll(records);

        return collection;
    }

    public RecordCollection all() {
        var collection = new LinearRecordCollection();
        collection.addAll(records);

        return collection;
    }

    public RecordCollection select(TableRecordSelector selector, RecordIterationLimiter limiter) {
        return all().select(selector, limiter);
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
        if (exists(destinationName)) {
            throw new QueryException(STR."Table '\{destinationName}' already exists.");
        }

        var databaseName = destinationName.getDatabaseName();
        var tableName = destinationName.getTableName();
        var database = Database.createIfDoesntExist(databaseName);
        var databaseDirectory = database.getDirectory();
        var destinationPath = databaseDirectory.getFilePath(tableName);

        directory.copy(destinationPath);

        return byName(destinationName);
    }

    public void copyScheme(TableName destinationName) {
        copy(destinationName).clear();
    }

    public void clear() {
        records.save(new LinearRecordCollection());
    }

    public TableVariable getVariable(String name) {
        return variableDirectory.getVariable(name);
    }

    public void setVariable(String name, Entity value) {
        var variable = getVariable(name);
        variable.set(value);
    }

    public void setVariable(String name, Object value) {
        setVariable(name, Entity.wrap(value));
    }

    public Optional<ColumnDescription> getColumn(String columnName) {
        return header.getColumn(columnName);
    }

    public boolean hasColumn(String columnName) {
        return getColumn(columnName).isPresent();
    }

    public boolean doesntHaveColumn(String columnName) {
        return !hasColumn(columnName);
    }

    public List<ColumnDescription> columns() {
        return header.getColumns();
    }

    @NotNull
    @Override
    public Iterator<TableRecord> iterator() {
        return all().iterator();
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

    public void addColumn(ColumnDescription description, RecordFieldGenerator generator) {
        var records = all();
        var columnName = description.name();
        var columnType = description.type();

        header.addColumn(description);

        description.tryGetGeneratingModifier().ifPresentOrElse(modifierEntity -> {
            var modifier = modifierEntity.getModifier();
            var modifierArguments = modifierEntity.getModifierArguments();

            for (var record : records) {
                var generatedField = RecordField.empty(columnName);

                modifier.prepareField(this, generatedField, modifierArguments);
                record.add(generatedField);
            }
        }, () -> {
            if (generator == null) {
                throw new QueryException("Add column needs a generator or a generating modifier.");
            }

            for (var record : records) {
                var generatedEntity = generator.apply(record);

                if (columnType.canCast(generatedEntity)) {
                    generatedEntity = generatedEntity.castTo(columnType);
                }

                if (generatedEntity.doesntHaveType(columnType)) {
                    throw new GeneratedValueMismatchesColumnTypeException(generator, description, generatedEntity);
                }

                record.add(new RecordField(columnName, generatedEntity));
            }
        });

        store(records);
        saveHeader();
    }

    public void addModifier(String columnName, ColumnModifierEntity modifier) {
        getColumn(columnName)
                .orElseThrow(() -> new ColumnNotFoundException(this, columnName))
                .addModifier(modifier);

        saveHeader();
    }

    public void change(TableRecordSelector selector, TableRecordChanger changer) {
        if (canNotUse(changer)) {
            throw new ColumnNotFoundException(this, changer.column());
        }

        var records = all();

        for (var record : records) {
            if (selector.selects(record)) {
                changer.change(record);
            }
        }

        store(records);
    }

    public int count(TableRecordSelector selector) {
        return all().select(selector, RecordIterationLimiter.UNLIMITED).count();
    }

    public void deleteColumn(String columnName) {
        if (doesntHaveColumn(columnName)) {
            throw new QueryException(STR."Table '\{getName()}' does not contain column '\{columnName}'.");
        }

        var records = all();
        records.forEach(record -> record.removeField(columnName));

        columns().removeIf(column -> column.name().equals(columnName));
        saveHeader();
        store(records);
    }

    public void delete(RecordLambda<Boolean> selector) {
        delete(selector, RecordIterationLimiter.UNLIMITED);
    }

    public void delete(RecordLambda<Boolean> selector, RecordIterationLimiter limiter) {
        var records = all();
        records.remove(selector, limiter);

        store(records);
    }

    public void deleteVariable(String name) {
        getVariable(name).delete();
    }

    public void exclude(TableRecordFinder finder) {
        var columnName = finder.columnName();
        var column = getColumn(columnName).orElseThrow(() -> new ColumnNotFoundException(this, columnName));
        var records = column.hasModifier("unique") ? new HashtableRecordCollection(columnName) : new PageTreeRecordCollection(columnName);

        records.addAll(this);
        records.exclude(finder);

        store(records);
    }

    public Optional<TableRecord> find(TableRecordFinder finder) {
        var columnName = finder.columnName();
        var column = getColumn(columnName).orElseThrow(() -> new ColumnNotFoundException(this, columnName));
        var records = column.hasModifier("unique") ?
                new HashtableRecordCollection(finder.columnName()) :
                new PageTreeRecordCollection(finder.columnName());

        records.addAll(this);
        records.forEach(records::add);

        return records.find(finder);
    }

    public void redefineColumn(ColumnDescription description) {
        if (header.doesntHaveColumn(description.name())) {
            throw new QueryException(STR."Column \{description.name()} does not exist.");
        }

        header.redefineColumn(description);
    }

    public void renameColumn(String columnName, String newName) {
        if (hasColumn(newName)) {
            throw new QuarkException(STR."The column \{newName} already exists.");
        }

        header.renameColumn(columnName, newName);
        saveHeader();
    }

    public void reorderColumns(List<String> order) {
        if (order.stream().anyMatch(columnName -> !header.hasColumn(columnName)) || header.getColumns().stream().anyMatch(columnDescription -> !order.contains(columnDescription.name()))) {
            throw new QueryException("A new order cannot be applied, because it has extra columns or missed some existing ones (your order: %s, existing order: %s).".formatted(
                    Lists.join(order),
                    Lists.join(header.getColumns(), ColumnDescription::name)
            ));
        }

        var records = all();

        for (var record : records) {
            record.reorderFields(order);
        }

        header.reorderColumns(order);
        header.save();
        store(records);
    }

    public void swapColumns(String firstColumnName, String secondColumnName) {
        header.swapColumns(firstColumnName, secondColumnName);
        header.save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(getName(), table.getName());
    }

    public boolean isSystem() {
        return database.isSystem();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
