package ru.anafro.quark.server.database.data.files;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.database.data.exceptions.*;
import ru.anafro.quark.server.database.language.entities.ColumnEntity;
import ru.anafro.quark.server.database.language.entities.ListEntity;
import ru.anafro.quark.server.utils.collections.Lists;
import ru.anafro.quark.server.utils.collections.Streams;
import ru.anafro.quark.server.utils.files.File;

import java.util.List;
import java.util.Optional;

import static ru.anafro.quark.server.database.language.Expressions.eval;

public class TableHeader {
    public static final String NAME = "Table's Header.qheader";
    private final Table table;
    private final File file;
    private List<ColumnDescription> columns;

    public TableHeader(Table table) {
        this.file = table.getDirectory().getFile(NAME);
        this.table = table;
        this.columns = Streams.toModifiableList(eval(file.read()).tryGetValueAsListOf(ColumnEntity.class).orElseThrow().stream().map(ColumnEntity::getValue));
    }

    public List<ColumnDescription> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDescription> columns) {
        this.columns = columns;
    }

    public Optional<ColumnDescription> getColumn(String columnName) {
        return columns.stream().filter(column -> columnName.equals(column.name())).findFirst();
    }

    public boolean hasColumn(String columnName) {
        return getColumn(columnName).isPresent();
    }

    public boolean missingColumn(String columnName) {
        return !hasColumn(columnName);
    }

    public void redefineColumn(ColumnDescription newColumnDescription) {
        columns.removeIf(columnDescription -> columnDescription.name().equals(newColumnDescription.name()));
        columns.add(newColumnDescription);
    }

    public void ensureRecordIsValid(TableRecord record) {
        if (columnCount() != record.fieldCount()) {
            throw new RecordFieldCountMismatchesTableHeaderException(table, record.fieldCount());
        }

        for (int index = 0; index < columns.size(); index++) {
            var field = record.fieldAt(index);
            var column = columnAt(index);
            var columnName = column.name();

            if (record.doesntHaveField(columnName) && column.isNotGenerated()) {
                throw new RecordFieldMissingException(record, column, table);
            }

            if (!field.getColumnName().equals(columnName)) {
                throw new RecordColumnsDisorderedException(record, column, table);
            }

            for (var modifierEntity : column.modifiers()) {
                var modifier = modifierEntity.getModifier();
                var modifierArguments = modifierEntity.getModifierArguments();
                var fieldType = field.getEntity().getType();
                var fieldValue = field.getEntity();

                if (!modifier.isFieldValid(table, field, modifierArguments)) {
                    throw new ColumnModifierValidityCheckFailedException(record, table, modifierEntity);
                }

                if (!modifier.isTypeAllowed(fieldType)) {
                    throw new ColumnModifierIsNotApplicableForProvidedTypeException(modifierEntity, fieldValue);
                }
            }
        }
    }

    public int columnCount() {
        return columns.size();
    }

    public ColumnDescription columnAt(int index) {
        return columns.get(index);
    }

    public void addColumn(ColumnDescription columnDescription) {
        columns.add(columnDescription);
    }

    public void save() {
        file.write(ListEntity.of(columns.stream().map(ColumnEntity::new).toList()).toInstructionForm());
    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return Lists.join(columns);
    }

    public boolean doesntHaveColumn(String columnName) {
        return !hasColumn(columnName);
    }
}
