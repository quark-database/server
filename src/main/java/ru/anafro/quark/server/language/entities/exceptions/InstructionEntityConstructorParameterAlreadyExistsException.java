package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class InstructionEntityConstructorParameterAlreadyExistsException extends DatabaseException {
    public InstructionEntityConstructorParameterAlreadyExistsException(String parameterName) {
        super(STR."An instruction's constructor parameter with name \{parameterName} already exists.");
    }
}
