package ru.anafro.quark.server.language.types;

import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.language.Expressions;
import ru.anafro.quark.server.language.entities.ChangerEntity;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.StringEntity;
import ru.anafro.quark.server.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.utils.types.exceptions.TypeException;

public class ChangerType extends EntityType<ChangerEntity> {
    public ChangerType() {
        super("changer", TableRecordChanger.class, ChangerEntity.class);
    }

    @Override
    public ChangerEntity makeEntity(String string) {
        var evaluatedEntity = Expressions.eval(string);

        if (evaluatedEntity.hasType(this)) {
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
                .argument(new StringEntity(changerEntity.getChanger().lambda()))
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return null;
    }
}
