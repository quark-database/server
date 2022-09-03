package ru.anafro.quark.server.databases.data.exceptions;

import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.types.EntityType;

import java.util.stream.Collectors;

public class ColumnModifierIsNotApplicableForProvidedTypeException extends DatabaseException {
    public ColumnModifierIsNotApplicableForProvidedTypeException(ColumnModifierEntity modifierEntity, Entity entity) {
        super("A column modifier %s is not applicable for the type %s. Only these types allowed: %s.".formatted(modifierEntity.getModifier(), entity.getExactTypeName(), modifierEntity.getModifier().getAllowedTypes().stream().map(EntityType::getName).collect(Collectors.joining(", "))));
    }
}
