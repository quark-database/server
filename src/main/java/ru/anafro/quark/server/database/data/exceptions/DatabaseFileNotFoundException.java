package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableRecords;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class DatabaseFileNotFoundException extends DatabaseFileException {
    public DatabaseFileNotFoundException(TableRecords tableRecords) {
        super("The table file %s does not exist.".formatted(quoted(tableRecords.getFilename())));
    }
}
