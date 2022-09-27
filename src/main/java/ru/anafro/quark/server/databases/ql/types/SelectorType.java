package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.SelectorEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class SelectorType extends EntityType {
    public SelectorType() {
        super("selector");
    }

    @Override
    public SelectorEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (SelectorEntity) evaluatedEntity;
        } else {
            throw new TypeException("SelectorType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if(entity.hasType(this)) {
            return new StringConstructorBuilder()
                    .name("selector")
                    .argument(new StringEntity(((SelectorEntity) entity).getSelector().expression()))
                    .build();
        } else {
            throw new TypeException("SelectorType.toInstructionForm(InstructionEntity) received an entity, but it has a wrong type: %s, but %s required.".formatted(this.getName(), entity.getType()));
        }
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
