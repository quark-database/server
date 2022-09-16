package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class ColumnModifierType extends EntityType {
    public ColumnModifierType() {
        super("modifier");
    }

    @Override
    public ColumnModifierEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (ColumnModifierEntity) evaluatedEntity;
        } else {
            throw new TypeException("ColumnModifierType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if(entity.hasType(this)) {
            var columnModifierEntity = (ColumnModifierEntity) entity;

            return new StringConstructorBuilder()
                    .name(columnModifierEntity.getValue().getName())
                    .argument(new StringEntity(columnModifierEntity.getColumnName()))
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
