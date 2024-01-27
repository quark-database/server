package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class RecordIterationLimiterInvalidParametersException extends DatabaseException {
    public RecordIterationLimiterInvalidParametersException(String message) {
        super(message);
    }
}
