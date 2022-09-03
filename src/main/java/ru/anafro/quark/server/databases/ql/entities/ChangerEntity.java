package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.TableRecordChanger;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

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
    public String getExactTypeName() {
        return getTypeName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }
}
