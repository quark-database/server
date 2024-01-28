package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class ConstructorHasNoVarargsException extends DatabaseException {
    public ConstructorHasNoVarargsException() {
        super("Something is trying to access the constructor varargs parameter, which does not exist.");
    }
}
