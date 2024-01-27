package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.Expressions;
import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.ListEntity;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

import java.util.List;

public class ListType extends EntityType<ListEntity> {
    public ListType() {
        super("list", List.class, ListEntity.class);
    }

    @Override
    public ListEntity makeEntity(String string) {   // TODO: Repeated code! (Check out other complicated types).
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
            return (ListEntity) evaluatedEntity;
        } else {
            throw new TypeException("ListType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        var list = (ListEntity) entity;
        var builder = new StringConstructorBuilder();

        if (list.isEmpty()) {
            return builder
                    .name("empty list of")
                    .argument(new StringEntity(list.getTypeNameOfElements()))
                    .build();
        } else {
            return builder                                      // TODO: type check (list)
                    .name(list.getTypeName())                   // list
                    .arguments(list.getValue())                 // values
                    .build();
        }
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
