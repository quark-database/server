package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.TableFile;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class ReadingTheNextLineOfTableFileFailedException extends DatabaseFileException {
    public ReadingTheNextLineOfTableFileFailedException(TableFile tableFile, Throwable cause) {
        super("Reading the next line of the table file %s failed, because of %s".formatted(quoted(tableFile.getFilename()), cause.toString()));
        initCause(cause);
    }
}
