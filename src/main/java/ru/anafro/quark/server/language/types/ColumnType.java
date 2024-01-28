package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.ColumnEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.StringEntity;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class ColumnType extends EntityType<ColumnEntity> {
    public ColumnType() {
        super("column", ColumnDescription.class, ColumnEntity.class);
    }


    @Override
    public ColumnEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
            return (ColumnEntity) evaluatedEntity;
        } else {
            throw new TypeException("ColumnType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        var columnDescription = entity.valueAs(ColumnDescription.class);

        return new StringConstructorBuilder()
                .name(columnDescription.type().getName())
                .argument(new StringEntity(columnDescription.name()))
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
