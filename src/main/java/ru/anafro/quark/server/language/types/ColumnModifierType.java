package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class ColumnModifierType extends EntityType<ColumnModifierEntity> {
    public ColumnModifierType() {
        super("modifier", ColumnModifier.class, ColumnModifierEntity.class);
    }

    @Override
    public ColumnModifierEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
            return (ColumnModifierEntity) evaluatedEntity;
        } else {
            throw new TypeException("ColumnModifierType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if (entity.hasType(this)) {
            var columnModifierEntity = (ColumnModifierEntity) entity;

            return new StringConstructorBuilder()
                    .name(columnModifierEntity.getValue().getName())
                    .arguments(columnModifierEntity.getModifierArguments())
                    .build();
        } else {
            throw new TypeException("ColumnModifierType.toInstructionForm(String) received an entity, but it has an unexpected type: %s, but %s required.".formatted(this.getName(), entity.getType()));
        }
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
