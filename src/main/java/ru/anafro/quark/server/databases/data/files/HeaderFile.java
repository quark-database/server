package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.exceptions.*;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.ColumnEntity;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.utils.containers.Lists;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderFile implements Savable {
    public static final String NAME = "Table's Header.qheader";
    private final Table ownerTable;
    private final List<ColumnDescription> columns;
    private final List<ColumnModifierEntity> modifiers;
    private final String filename;

    public HeaderFile(Table table) {
        this.filename = table.getDatabase().getFolder().getAbsolutePath() + File.separator + table.getName() + File.separator + NAME;

        try {
            this.ownerTable = table;

            var lines = Files.readAllLines(Path.of(filename));

            this.columns = ((ArrayList<ColumnEntity>) ConstructorEvaluator.eval(lines.get(0)).getValue()).stream().map(ColumnEntity::getValue).collect(Collectors.toList());
            this.modifiers = ((ArrayList<ColumnModifierEntity>) ConstructorEvaluator.eval(lines.get(1)).getValue());

            modifiers.sort(Comparator.comparing(entity -> entity.getValue().getApplicationPriority()));
        } catch (Exception exception) {
            throw new HeaderFileReadingFailedException(filename, exception);
        }
    }

    public String getFilename() {
        return filename;
    }

    public List<ColumnDescription> getColumns() {
        return columns;
    }

    public List<ColumnModifierEntity> getModifiers() {
        return modifiers;
    }

    public ColumnDescription getColumn(String columnName) {
        for(var column : columns) {
            if(column.getName().equals(columnName)) {
                return column;
            }
        }

        return null;
    }

    public boolean hasColumn(String columnName) {
        return getColumn(columnName) != null;
    }

    public boolean missingColumn(String columnName) {
        return !hasColumn(columnName);
    }

    public void redefineColumn(ColumnDescription newColumnDescription) {
        columns.removeIf(columnDescription -> columnDescription.getName().equals(newColumnDescription.getName()));
        columns.add(newColumnDescription);
    }

    public void requireValidity(TableRecord record) {
        if(columnCount() != record.fieldCount()) {
            throw new RecordFieldCountMismatchesTableHeaderException(ownerTable, record.fieldCount());
        }

        for(int index = 0; index < columns.size(); index++) {
            ColumnDescription column = columnAt(index);

            if(record.missingField(column.getName()) && modifiers.stream().noneMatch(modifierEntity -> modifierEntity.getColumnName().equals(column.getName()) && modifierEntity.getModifier().areValuesShouldBeGenerated())) {
                throw new RecordFieldMissingException(record, column, ownerTable);
            }

            if(!record.fieldAt(index).getColumnName().equals(column.getName())) {
                throw new RecordColumnsDisorderedException(record, column, ownerTable);
            }
        }

        for(var modifierEntity : modifiers) {
            if(record.missingField(modifierEntity.getColumnName())) {
                throw new ColumnModifierColumnMissingException(record, modifierEntity, ownerTable);
            }

            if(!modifierEntity.getModifier().isFieldSuitable(ownerTable, record.getField(modifierEntity.getColumnName()), modifierEntity.getModifierArguments())) {
                throw new ColumnModifierValidityCheckFailedException(record, ownerTable, modifierEntity);
            }

            if(!modifierEntity.getModifier().isTypeAllowed(record.getField(modifierEntity.getColumnName()).getValue().getType())) {
                throw new ColumnModifierIsNotApplicableForProvidedTypeException(modifierEntity, record.getField(modifierEntity.getColumnName()).getValue());
            }
        }
    }

    public int columnCount() {
        return columns.size();
    }

    public ColumnDescription columnAt(int index) {
        return columns.get(index);
    }

    @Deprecated(since = "Quark 1.1", forRemoval = true)
    public void runBeforeRecordInsertionActionOfModifiers(TableRecord record) {
        modifiers.sort(Comparator.comparing(modifierEntity -> modifierEntity.getModifier().getApplicationPriority()));

        for(var modifier : modifiers) {
            modifier.getModifier().beforeRecordInsertion(ownerTable, record.getField(modifier.getColumnName()), modifier.getModifierArguments());
        }
    }

    public void addColumn(ColumnDescription columnDescription) {
        modifiers.addAll(columnDescription.getModifiers());
        columns.add(new ColumnDescription(columnDescription.getName(), columnDescription.getType(), Lists.empty()));
    }

    @Override
    public void save() {
        try {
            var lines = new TextBuffer();

            lines.appendLine(ListEntity.of(columns.stream().map(ColumnEntity::new).toList()).toInstructionForm());
            lines.appendLine(ListEntity.of(modifiers).toInstructionForm());

            Files.writeString(Path.of(getFilename()), lines);
        } catch (IOException exception) {
            throw new HeaderFileWritingFailedException(this, exception);
        }
    }

    public Table getOwnerTable() {
        return ownerTable;
    }
}
