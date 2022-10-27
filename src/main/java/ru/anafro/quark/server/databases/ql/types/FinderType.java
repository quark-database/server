package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class FinderType extends EntityType {
    public FinderType() {
        super("finder");
    }

    @Override
    public FinderEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (FinderEntity) evaluatedEntity;
        } else {
            throw new TypeException("FinderType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        var finderEntity = (FinderEntity) entity;

        return new StringConstructorBuilder()
                .name("finder")
                .argument(new StringEntity(finderEntity.getValue().getColumnName()))
                .argument(finderEntity.getValue().getFindingValue())
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
