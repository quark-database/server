package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.data.exceptions.DatabaseFileNotFoundException;
import ru.anafro.quark.server.databases.data.exceptions.ReadingTheNextLineOfTableFileFailedException;

import java.io.*;
import java.util.Iterator;

public class TableFile implements Iterable<UntypedTableRecord> {
    private final String filename;
    private final File file;

    public TableFile(String filename) {
        this.filename = filename;
        this.file = new File(filename);
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
        private final TableFile tableFile;
        private final BufferedReader tableFileBufferedReader;
        private boolean readerNextLineResultStored = false;
        private String bufferedFileLine = null;

        public TableFileRecordIterator(TableFile tableFile) {
            try {
                this.tableFile = tableFile;
                this.tableFileBufferedReader = new BufferedReader(new FileReader(tableFile.getFile()));
            } catch (FileNotFoundException exception) {
                throw new DatabaseFileNotFoundException(tableFile);
            }
        }

        protected TableFile getTableFile() {
            return tableFile;
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
                    throw new ReadingTheNextLineOfTableFileFailedException(tableFile, exception);
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
