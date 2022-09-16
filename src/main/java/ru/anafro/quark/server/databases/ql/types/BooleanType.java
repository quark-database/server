package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.BooleanEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class BooleanType extends EntityType {
    public BooleanType() {
        super("boolean");
    }

    @Override
    public BooleanEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (BooleanEntity) evaluatedEntity;
        } else {
            throw new TypeException("BooleanType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if(entity.hasType(this)) {
            var booleanEntity = (BooleanEntity) entity;

            return new StringConstructorBuilder()
                    .name(booleanEntity.getValue() ? "yes" : "no")
                    .build();
        } else {
            throw new TypeException("ListType.toInstructionForm(String) received an entity, but it has an unexpected type: %s, but %s required.".formatted(this.getName(), entity.getType()));
        }
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
