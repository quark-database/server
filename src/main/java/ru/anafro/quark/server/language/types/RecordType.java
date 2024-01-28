package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.TableRecord;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.ListEntity;
import ru.anafro.quark.server.language.entities.RecordEntity;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class RecordType extends EntityType<RecordEntity> {
    public RecordType() {
        super("record", TableRecord.class, RecordEntity.class, "list");
    }

    @Override
    public RecordEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
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
        if (entity.hasExactType("list")) {
            return new RecordEntity(((ListEntity) entity).getValue().toArray(new Entity[0]));
        }

        return null;
    }
}
