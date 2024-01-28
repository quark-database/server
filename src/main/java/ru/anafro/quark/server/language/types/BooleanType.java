package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.BooleanEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.Booleans;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class BooleanType extends EntityType<BooleanEntity> {
    public BooleanType() {
        super("boolean", boolean.class, BooleanEntity.class, "str");
    }

    @Override
    public BooleanEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
            return (BooleanEntity) evaluatedEntity;
        } else {
            throw new TypeException("BooleanType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if (entity.hasType(this)) {
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
        if (entity.hasExactType("str")) {
            return Entity.wrap(Booleans.createFromString(entity.valueAs(String.class)));
        }

        return null;
    }
}
