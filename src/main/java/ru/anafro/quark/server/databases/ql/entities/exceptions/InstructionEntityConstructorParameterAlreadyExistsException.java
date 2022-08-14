package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionEntityConstructorParameterAlreadyExistsException extends DatabaseException {
    public InstructionEntityConstructorParameterAlreadyExistsException(String parameterName) {
        super("An instruction's constructor parameter with name %s already exists.".formatted(quoted(parameterName)));
    }
}
