package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.DateEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.LongEntity;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

import java.util.Date;

public class DateType extends EntityType<DateEntity> {
    public DateType() {
        super("date", Date.class, DateEntity.class, "long");
    }

    @Override
    public DateEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
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
        if (entity.hasExactType("long")) {
            return new DateEntity(new Date(entity.valueAs(Long.class)));
        }

        return null;
    }
}
