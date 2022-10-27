package ru.anafro.quark.server.databases.ql.entities.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.Entity;

public class EntitiesCannotBeComparedException extends DatabaseException {
    public EntitiesCannotBeComparedException(Entity first, Entity second) {
        super("Tried to compare an entity %s with type %s, and an entity %s with type %s, but they have different types. You can't compare them.".formatted(
                first.toInstructionForm(),
                first.getExactTypeName(),
                second.toInstructionForm(),
                second.getExactTypeName()
        ));
    }
}
