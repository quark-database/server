package ru.anafro.quark.server.databases.ql.entities.constructors.exceptions;

import ru.anafro.quark.server.databases.exceptions.QueryException;

public class ConstructorRuntimeException extends QueryException {
    public ConstructorRuntimeException(String message) {
        super(message);
    }
}
