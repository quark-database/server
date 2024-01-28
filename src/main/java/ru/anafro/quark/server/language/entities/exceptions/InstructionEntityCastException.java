package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionEntityCastException extends DatabaseException {
    public InstructionEntityCastException(Object castingObject, Class<?> classToCastTo) {
        super("Instruction entity that contains object with a type %s is being tried to convert to type %s.".formatted(quoted(castingObject.getClass().getSimpleName()), quoted(classToCastTo.getSimpleName())));
    }
}
