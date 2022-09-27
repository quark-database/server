package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;

public class ConstructorEvaluationException extends DatabaseException {
    public ConstructorEvaluationException(EntityConstructor constructor, String message) {
        super("Error in the constructor %s: %s".formatted(constructor.getSyntax(), message));
    }
}
