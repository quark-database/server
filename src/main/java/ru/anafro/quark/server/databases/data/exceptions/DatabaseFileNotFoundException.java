package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.RecordsFile;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class DatabaseFileNotFoundException extends DatabaseFileException {
    public DatabaseFileNotFoundException(RecordsFile recordsFile) {
        super("The table file %s does not exist.".formatted(quoted(recordsFile.getFilename())));
    }
}
