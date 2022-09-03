package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class ListType extends EntityType {
    public ListType() {
        super("list");
    }

    @Override
    public ListEntity makeEntity(String string) {   // TODO: Repeated code! (Check out other complicated types).
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (ListEntity) evaluatedEntity;
        } else {
            throw new TypeException("ListType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity list) {
        return new StringConstructorBuilder()       // TODO: type check (list)
                .name(list.getType().getName())             // list
                .arguments(((ListEntity) list).getValue())  // values
                .build();
    }
}
