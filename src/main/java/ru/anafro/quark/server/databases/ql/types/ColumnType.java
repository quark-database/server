package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class ColumnType extends EntityType {
    public ColumnType() {
        super("column");
    }


    @Override
    public ColumnEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (ColumnEntity) evaluatedEntity;
        } else {
            throw new TypeException("ColumnType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        var columnDescription = entity.valueAs(ColumnDescription.class);

        return new StringConstructorBuilder()
                .name(columnDescription.getType().getName())
                .argument(new StringEntity(columnDescription.getName()))
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
