package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.*;
import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.ReadingTheNextLineOfTableFileFailedException;
import ru.anafro.quark.server.databases.data.exceptions.RecordsFileInsertionFailedException;
import ru.anafro.quark.server.databases.data.exceptions.RecordsFileWritingFailedException;
import ru.anafro.quark.server.databases.data.structures.RecordCollection;
import ru.anafro.quark.server.databases.data.structures.RecordCollectionResolver;
import ru.anafro.quark.server.utils.strings.TextBuffer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class RecordsFile implements Iterable<TableRecord> {
    public static final String NAME = "Table's Records.qrecords";
    private final String filename;
    private final File file;
    private final Table table;

    public RecordsFile(Table table) {
        this.filename = table.getDatabase().getFolder().getAbsolutePath() + File.separator + table.getName() + File.separator + NAME;
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
        var records = table.loadRecords(new RecordCollectionResolver(RecordCollectionResolver.RecordCollectionResolverCase.SELECTOR_IS_TOO_COMPLEX));
        records.forEach(record -> {
            if(selector.shouldBeSelected(record)) {
                changer.change(record);
            }
        });

        save(records);
    }

    @Override
    public Iterator<TableRecord> iterator() {
        return new TableFileRecordIterator(this);
    }

    public void insert(TableRecord record) {
        try(var bufferedWriter = new BufferedWriter(new FileWriter(file, true))){
            bufferedWriter.newLine();
            bufferedWriter.write(record.toTableLine());
        } catch(IOException exception) {
            throw new RecordsFileInsertionFailedException(this, record, exception);
        }
    }

    public void save(RecordCollection collection) {
        try {
            var lines = new TextBuffer();

            for(var records : collection) {
                lines.appendLine(records.toTableLine());
            }

            Files.writeString(Path.of(file.getPath()), lines);
        } catch (IOException exception) {
            throw new RecordsFileWritingFailedException(this, exception);
        }
    }

    private static class TableFileRecordIterator implements Iterator<TableRecord> {
        private final RecordsFile recordsFile;
        private final BufferedReader tableFileBufferedReader;
        private boolean readerNextLineResultStored = false;
        private String bufferedFileLine = null;

        public TableFileRecordIterator(RecordsFile recordsFile) {
            try {
                this.recordsFile = recordsFile;
                this.tableFileBufferedReader = new BufferedReader(new FileReader(recordsFile.getFile()));
            } catch (IOException exception) {
                throw new DatabaseFileNotFoundException(recordsFile);
            }
        }

        protected RecordsFile getTableFile() {
            return recordsFile;
        }

        protected BufferedReader getTableFileBufferedReader() {
            return tableFileBufferedReader;
        }

        private void readNextLineToBufferIfDidNot() {
            if(!readerNextLineResultStored) {
                try {
                    do {
                        bufferedFileLine = tableFileBufferedReader.readLine();
                        this.readerNextLineResultStored = true;

                        if(bufferedFileLine == null) {
                            break;
                        }

                    } while (bufferedFileLine.isBlank());
                } catch (IOException exception) {
                    throw new ReadingTheNextLineOfTableFileFailedException(recordsFile, exception);
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

            return UntypedTableRecord.fromString(getBufferedFileLine()).applyTypesFrom(recordsFile.getTable().getHeader());
        }
    }
}
