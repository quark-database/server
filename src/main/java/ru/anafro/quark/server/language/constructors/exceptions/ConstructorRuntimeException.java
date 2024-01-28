package ru.anafro.quark.server.language.constructors.exceptions;

import ru.anafro.quark.server.database.exceptions.QueryException;

public class ConstructorRuntimeException extends QueryException {
    public ConstructorRuntimeException(String message) {
        super(message);
    }
}
