package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class BadInstructionEntityConstructorArgumentStateException extends DatabaseException {
    public BadInstructionEntityConstructorArgumentStateException() {
        super("A constructor's argument is being tried to compute, but it is not in a right state: entity should be null, but constructor should not.");
    }
}
