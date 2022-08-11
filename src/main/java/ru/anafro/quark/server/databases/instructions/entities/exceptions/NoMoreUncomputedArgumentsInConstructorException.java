package ru.anafro.quark.server.databases.instructions.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;

public class NoMoreUncomputedArgumentsInConstructorException extends DatabaseException {
    public NoMoreUncomputedArgumentsInConstructorException() {
        super("A constructor is being tried to get the next uncomputed argument, but there are no more uncomputed arguments. Did you forget to check .hasUncomputedArguments() in an InstructionEntityConstructor?");
    }
}
