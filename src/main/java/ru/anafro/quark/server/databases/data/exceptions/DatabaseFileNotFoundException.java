package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.TableFile;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class DatabaseFileNotFoundException extends DatabaseFileException {
    public DatabaseFileNotFoundException(TableFile tableFile) {
        super("The table file %s does not exist.".formatted(quoted(tableFile.getFilename())));
    }
}
