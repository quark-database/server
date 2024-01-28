package ru.anafro.quark.server.database.language.entities.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.language.entities.EntityConstructor;

public class ConstructorEvaluationException extends DatabaseException {
    public ConstructorEvaluationException(EntityConstructor constructor, String message) {
        super("Error in the constructor %s: %s".formatted(constructor.getSyntax(), message));
    }
}