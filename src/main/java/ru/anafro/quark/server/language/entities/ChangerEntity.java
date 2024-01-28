package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.TableRecordChanger;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.facade.Quark;

public class ChangerEntity extends Entity {
    private final TableRecordChanger changer;

    public ChangerEntity(TableRecordChanger changer) {
        super("changer");
        this.changer = changer;
    }

    public TableRecordChanger getChanger() {
        return changer;
    }

    @Override
    public TableRecordChanger getValue() {
        return changer;
    }

    @Override
    public String format() {
        return new StringConstructorBuilder()
                .name(getTypeName())
                .argument(new StringEntity(changer.lambda()))
                .build();
    }

    @Override
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }

    @Override
    public int rawCompare(Entity entity) {
        return changer.lambda().compareTo(((ChangerEntity) entity).getChanger().lambda());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(changer.lambda());
    }
}
