package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.TableRecord;
import ru.anafro.quark.server.databases.data.exceptions.HeaderFileReadingFailedException;
import ru.anafro.quark.server.databases.ql.EntityEvalService;
import ru.anafro.quark.server.databases.ql.entities.ColumnEntity;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderFile {
    public static final String NAME = "Table's Header.qheader";
    private final Table ownerTable;
    private final List<ColumnDescription> columns;
    private final List<ColumnModifier> modifiers;
    private final String filename;

    public HeaderFile(Table table) {
        this.filename = table.getDatabase().getFolder().getAbsolutePath() + File.separator + table.getName() + File.separator + NAME;

        try {
            this.ownerTable = table;

            var lines = Files.readAllLines(Path.of(filename));
            this.columns = ((ArrayList<ColumnEntity>) EntityEvalService.eval(lines.get(0)).getValue()).stream().map(ColumnEntity::getValue).collect(Collectors.toList());
            this.modifiers = ((ArrayList<ColumnModifierEntity>) EntityEvalService.eval(lines.get(1)).getValue()).stream().map(ColumnModifierEntity::getValue).collect(Collectors.toList());

            modifiers.sort(Comparator.comparing(ColumnModifier::getApplicationPriority));
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

    public List<ColumnModifier> getModifiers() {
        return modifiers;
    }

    public boolean isRecordValid(TableRecord record) {
        for(var modifier : modifiers) {
            if(!modifier.checkValidity(ownerTable, record)) {
                return false;
            }
        }

        return true;
    }
}
