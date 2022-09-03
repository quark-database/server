package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.ql.ConstructorEvaluator;
import ru.anafro.quark.server.databases.ql.entities.ChangerEntity;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.types.exceptions.TypeException;

public class ChangerType extends EntityType {
    public ChangerType() {
        super("changer");
    }

    @Override
    public ChangerEntity makeEntity(String string) {
        var evaluatedEntity = ConstructorEvaluator.eval(string);

        if(evaluatedEntity.hasType(this)) {
            return (ChangerEntity) evaluatedEntity;
        } else {
            throw new TypeException("ChangerType.makeEntity(String) received a string, but after evaluation it has an unexpected type: %s, but %s required.".formatted(this.getName(), evaluatedEntity.getType()));
        }
    }

    @Override
    public String toInstructionForm(Entity entity) {
        var changerEntity = new ChangerEntity(entity.valueAs(TableRecordChanger.class));

        return new StringConstructorBuilder()
                .name(changerEntity.getType().getName())
                .argument(new StringEntity(changerEntity.getChanger().expression()))
                .build();
    }
}
