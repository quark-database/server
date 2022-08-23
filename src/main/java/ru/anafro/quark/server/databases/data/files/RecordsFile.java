package ru.anafro.quark.server.databases.data.files;

import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.data.UntypedTableRecord;
import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.ReadingTheNextLineOfTableFileFailedException;

import java.io.*;
import java.util.Iterator;

public class RecordsFile implements Iterable<UntypedTableRecord> {
    public static final String NAME = "Table's Records.qrecords";
    private final String filename;
    private final File file;

    public RecordsFile(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public RecordsFile(Table table) {
        this(table.getDatabase().getFolder().getAbsolutePath() + File.separator + table.getName() + File.separator + NAME);
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return file;
    }

    @Override
    public Iterator<UntypedTableRecord> iterator() {
        return new TableFileRecordIterator(this);
    }

    private static class TableFileRecordIterator implements Iterator<UntypedTableRecord> {
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
        public UntypedTableRecord next() {
            readNextLineToBufferIfDidNot();
            requireBufferUpdateNextTime();

            return UntypedTableRecord.fromString(getBufferedFileLine());
        }
    }

}
