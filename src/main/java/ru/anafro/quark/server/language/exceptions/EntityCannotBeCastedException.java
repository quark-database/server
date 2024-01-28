package ru.anafro.quark.server.language.exceptions;

import ru.anafro.quark.server.database.exceptions.QueryException;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.types.EntityType;

public class EntityCannotBeCastedException extends QueryException {
    public EntityCannotBeCastedException(Entity entity, EntityType<?> targetType) {
        this(entity, targetType.getName());
    }

    public EntityCannotBeCastedException(Entity entity, String targetType) {
        super("Entity '%s' with type %s cannot be casted to type %s.".formatted(
                entity.toInstructionForm(),
                entity.getExactTypeName(),
                targetType
        ));
    }
}
