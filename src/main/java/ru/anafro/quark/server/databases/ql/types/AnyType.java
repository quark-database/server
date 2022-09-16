package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;

public class AnyType extends EntityType {
    public AnyType() {
        super("any", CASTABLE_FROM_ANY_TYPE);
    }

    @Override
    public Entity makeEntity(String string) {
        return ConstructorEvaluator.eval(string);
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return null;
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return entity;
    }
}
