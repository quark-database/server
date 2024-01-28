package ru.anafro.quark.server.database.data.exceptions;

import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.types.EntityType;

import java.util.stream.Collectors;

public class ColumnModifierIsNotApplicableForProvidedTypeException extends DatabaseException {
    public ColumnModifierIsNotApplicableForProvidedTypeException(ColumnModifierEntity modifierEntity, Entity entity) {
        super("A column modifier %s is not applicable for the type %s. Only these types allowed: %s.".formatted(modifierEntity.getModifier(), entity.getExactTypeName(), modifierEntity.getModifier().getAllowedTypes().stream().map(EntityType::getName).collect(Collectors.joining(", "))));
    }
}
