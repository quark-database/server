package ru.anafro.quark.server.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;

public class InstructionEntityCastException extends DatabaseException {
    public InstructionEntityCastException(Object castingObject, Class<?> classToCastTo) {
        super(STR."Instruction entity that contains object with a type '\{castingObject.getClass().getSimpleName()}' is being tried to convert to type '\{classToCastTo.getSimpleName()}'.");
    }
}
