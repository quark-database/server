package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.RecordsFile;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ReadingTheNextLineOfTableFileFailedException extends DatabaseFileException {
    public ReadingTheNextLineOfTableFileFailedException(RecordsFile recordsFile, Throwable cause) {
        super("Reading the next line of the table file %s failed, because of %s".formatted(quoted(recordsFile.getFilename()), cause.toString()));
        initCause(cause);
    }
}
