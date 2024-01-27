package ru.anafro.quark.server.database.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorParameter;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class VarargsParameterInConstructorAlreadyExistsException extends DatabaseException {
    public VarargsParameterInConstructorAlreadyExistsException(InstructionEntityConstructorParameter existingVarargs) {
        super("A constructor has varargs parameter %s, but you are trying to add a new varargs parameter %s".formatted(quoted(existingVarargs.name()), quoted(existingVarargs.name())));
    }
}
