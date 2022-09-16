package ru.anafro.quark.server.databases.ql.exceptions;

import ru.anafro.quark.server.databases.exceptions.QueryException;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.types.EntityType;

public class EntityCannotBeCastedException extends QueryException {
    public EntityCannotBeCastedException(Entity entity, EntityType targetType) {
        super("Entity '%s' with type %s cannot be casted to type %s.".formatted(
                entity.toInstructionForm(),
                entity.getExactTypeName(),
                targetType.getName()
        ));
    }
}
