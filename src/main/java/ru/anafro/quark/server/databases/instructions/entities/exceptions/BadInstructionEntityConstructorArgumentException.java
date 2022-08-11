package ru.anafro.quark.server.databases.instructions.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.instructions.entities.InstructionEntityConstructorArgument;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class BadInstructionEntityConstructorArgumentException extends DatabaseException {
    public BadInstructionEntityConstructorArgumentException() {
        super("A constructor's argument is being tried to compute, but it is not in a right state: entity should be null, but constructor should not.");
    }
}
