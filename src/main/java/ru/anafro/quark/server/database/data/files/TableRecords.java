package ru.anafro.quark.server.database.data.files;

import org.jetbrains.annotations.NotNull;
import ru.anafro.quark.server.database.data.*;
import ru.anafro.quark.server.database.data.exceptions.DatabaseFileNotFoundException;
import ru.anafro.quark.server.database.data.exceptions.ReadingTheNextLineOfTableFileFailedException;
import ru.anafro.quark.server.database.data.exceptions.RecordsFileInsertionFailedException;
import ru.anafro.quark.server.database.data.exceptions.RecordsFileWritingFailedException;
import ru.anafro.quark.server.database.data.structures.RecordCollection;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class TableRecords implements Iterable<TableRecord> {
    public static final String NAME = "Table's Records.qrecords";
    private final String filename;
    private final File file;
    private final Table table;

    public TableRecords(Table table) {
        this.filename = table.getDatabase().getDirectory().getAbsoluteFilePath(table.getName(), NAME);
        this.file = new File(filename);
        this.table = table;
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return file;
    }

    public Table getTable() {
        return table;
    }

    public void change(TableRecordChanger changer, ExpressionTableRecordSelector selector) {
        var records = table.all();
        records.forEach(record -> {
            if (selector.selects(record)) {
                changer.change(record);
            }
        });

        save(records);
    }

    @NotNull
    @Override
    public Iterator<TableRecord> iterator() {
        return new InMemoryTableFileRecordIterator(this).iterator();
    }

    public void insert(TableRecord record) {
        table.getHeader().ensureRecordIsValid(record);
        table.getHeader().prepareRecord(record);
        try (var bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(record.toTableLine());
            bufferedWriter.newLine();
        } catch (IOException exception) {
            throw new RecordsFileInsertionFailedException(this, record, exception);
        }
    }

    public void save(RecordCollection collection) {
        try {
            var lines = new TextBuffer();

            for (var records : collection) {
                lines.appendLine(records.toTableLine());
            }

            Files.writeString(Path.of(file.getPath()), lines);
        } catch (IOException exception) {
            throw new RecordsFileWritingFailedException(this, exception);
        }
    }

    private static class InMemoryTableFileRecordIterator implements Iterable<TableRecord> {
        private final TableRecords tableRecords;
        private final List<TableRecord> records;

        public InMemoryTableFileRecordIterator(TableRecords tableRecords) {
            this.tableRecords = tableRecords;
            this.records = new ru.anafro.quark.server.utils.files.File(tableRecords.getFile().toPath())
                    .readLines()
                    .stream()
                    .map(UntypedTableRecord::fromString)
                    .map(record -> record.applyTypesFrom(tableRecords.getTable().getHeader()))
                    .toList();
        }

        @NotNull
        @Override
        public Iterator<TableRecord> iterator() {
            return records.iterator();
        }
    }

    @Deprecated(since = "3")
    private static class TableFileRecordIterator implements Iterator<TableRecord> {
        private final TableRecords tableRecords;
        private BufferedReader tableFileBufferedReader = null;
        private boolean readerNextLineResultStored = false;
        private String bufferedFileLine = null;

        public TableFileRecordIterator(TableRecords tableRecords) {
            try {
                this.tableRecords = tableRecords;
                this.tableFileBufferedReader = new BufferedReader(new FileReader(tableRecords.getFile()));
            } catch (IOException exception) {
                throw new DatabaseFileNotFoundException(tableRecords);
            }
        }

        private void readNextLineToBufferIfDidNot() {
            if (!readerNextLineResultStored) {
                try {
                    do {
                        bufferedFileLine = tableFileBufferedReader.readLine();
                        this.readerNextLineResultStored = true;

                        if (bufferedFileLine == null) {
                            break;
                        }

                    } while (bufferedFileLine.isBlank());
                } catch (IOException exception) {
                    throw new ReadingTheNextLineOfTableFileFailedException(tableRecords, exception);
                }
            }
        }

        private void requireBufferUpdateNextTime() {
            this.readerNextLineResultStored = false;
        }

        @Override
        public boolean hasNext() {
            readNextLineToBufferIfDidNot();

            return bufferedFileLine != null;
        }

        public String getBufferedFileLine() {
            return bufferedFileLine;
        }

        @Override
        public TableRecord next() {
            readNextLineToBufferIfDidNot();
            requireBufferUpdateNextTime();

            return UntypedTableRecord.fromString(getBufferedFileLine()).applyTypesFrom(tableRecords.getTable().getHeader());
        }
    }
}
