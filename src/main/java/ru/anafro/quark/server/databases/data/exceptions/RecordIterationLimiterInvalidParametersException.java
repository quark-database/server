package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class RecordIterationLimiterInvalidParametersException extends DatabaseException {
    public RecordIterationLimiterInvalidParametersException(String message) {
        super(message);
    }
}
