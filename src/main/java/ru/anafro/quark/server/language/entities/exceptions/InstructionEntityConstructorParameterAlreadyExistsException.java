package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionEntityConstructorParameterAlreadyExistsException extends DatabaseException {
    public InstructionEntityConstructorParameterAlreadyExistsException(String parameterName) {
        super("An instruction's constructor parameter with name %s already exists.".formatted(quoted(parameterName)));
    }
}
