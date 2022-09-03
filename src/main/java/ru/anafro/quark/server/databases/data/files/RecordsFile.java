package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.*;
import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.ReadingTheNextLineOfTableFileFailedException;
import ru.anafro.quark.server.databases.data.exceptions.RecordsFileInsertionFailedException;

import java.io.*;
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

    public void change(TableRecordChanger changer, TableRecordSelector selector) {
        // TODO
    }

    @Override
    public Iterator<TableRecord> iterator() {
        return new TableFileRecordIterator(this);
    }

    public void insert(TableRecord record) {
        try(var bufferedWriter = new BufferedWriter(new FileWriter(file, true))){
            bufferedWriter.write(record.toTableLine());
        } catch(IOException exception) {
            throw new RecordsFileInsertionFailedException(this, record, exception);
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
            } catch (FileNotFoundException exception) {
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
                    this.bufferedFileLine = tableFileBufferedReader.readLine();
                    this.readerNextLineResultStored = true;
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
