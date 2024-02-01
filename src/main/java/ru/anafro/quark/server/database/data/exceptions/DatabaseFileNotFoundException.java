package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.data.files.TableRecords;

public class DatabaseFileNotFoundException extends DatabaseFileException {
    public DatabaseFileNotFoundException(TableRecords tableRecords) {
        super(STR."The table file '\{tableRecords.getFilename()}' does not exist.");
    }
}
