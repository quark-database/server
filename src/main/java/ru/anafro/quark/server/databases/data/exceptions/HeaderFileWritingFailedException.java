package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.data.files.HeaderFile;

public class HeaderFileWritingFailedException extends DatabaseFileException {
    public HeaderFileWritingFailedException(HeaderFile headerFile, Throwable causedBy) {
        super("Writing to %s table's file header inside the database %s failed, because of %s: %s.".formatted(
                headerFile.getOwnerTable().getName(),
                headerFile.getOwnerTable().getDatabase().getName(),
                causedBy.getClass().getSimpleName(),
                causedBy.getMessage()
            )
        );

        initCause(causedBy);
    }
}
