package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.ListEntity;
import ru.anafro.quark.server.databases.ql.entities.RecordEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class RecordType extends EntityType {
    public RecordType() {
        super("record", "list");
    }

    @Override
    public RecordEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (RecordEntity) evaluatedEntity;
        } else {
            throw new TypeException("RecordType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return new StringConstructorBuilder()
                .name(entity.getTypeName())
                .arguments(((RecordEntity) entity).getValues())
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return switch(entity.getTypeName()) {
            case "list" -> new RecordEntity(((ListEntity) entity).getValue().toArray(new Entity[0]));
            default -> null;
        };
    }
}
