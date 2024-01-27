package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableRecords;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ReadingTheNextLineOfTableFileFailedException extends DatabaseFileException {
    public ReadingTheNextLineOfTableFileFailedException(TableRecords tableRecords, Throwable cause) {
        super("Reading the next line of the table file %s failed, because of %s".formatted(quoted(tableRecords.getFilename()), cause.toString()));
        initCause(cause);
    }
}
