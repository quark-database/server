package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter;

public class VarargsParameterInConstructorAlreadyExistsException extends DatabaseException {
    public VarargsParameterInConstructorAlreadyExistsException(InstructionEntityConstructorParameter existingVarargs) {
        super(STR."A constructor has varargs parameter '\{existingVarargs.name()}', but you are trying to add a new varargs parameter \{existingVarargs.name()}");
    }
}
