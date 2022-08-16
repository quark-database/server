package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;

public class ConstructorEvaluationException extends DatabaseException {
    public ConstructorEvaluationException(InstructionEntityConstructor constructor, String message) {
        super("Error in the constructor %s: %s".formatted(constructor.getSyntax(), message));
    }
}
