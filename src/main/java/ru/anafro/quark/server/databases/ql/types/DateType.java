package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.DateEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.LongEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

import java.util.Date;

public class DateType extends EntityType {
    public DateType() {
        super("date", "long");
    }

    @Override
    public DateEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (DateEntity) evaluatedEntity;
        } else {
            throw new TypeException("DateType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        return new StringConstructorBuilder()
                .name("date from stamp")
                .argument(new LongEntity(((DateEntity) entity).getDate().getTime()))
                .build();
    }

    @Override
    protected DateEntity castOrNull(Entity entity) {
        return switch(entity.getTypeName()) {
            case "long" -> new DateEntity(new Date(entity.valueAs(Long.class)));
            default -> null;
        };
    }
}
