package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.TableRecordFinder;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.FinderEntity;
import ru.anafro.quark.server.language.entities.StringEntity;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class FinderType extends EntityType<FinderEntity> {
    public FinderType() {
        super("finder", TableRecordFinder.class, FinderEntity.class);
    }

    @Override
    public FinderEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
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
                .argument(new StringEntity(finderEntity.getValue().columnName()))
                .argument(finderEntity.getValue().findingValue())
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
