package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableRecords;

public class ReadingTheNextLineOfTableFileFailedException extends DatabaseFileException {
    public ReadingTheNextLineOfTableFileFailedException(TableRecords tableRecords, Throwable cause) {
        super(STR."Reading the next line of the table file '\{tableRecords.getFilename()}' failed, because of \{cause.toString()}");
        initCause(cause);
    }
}
