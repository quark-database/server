package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.TableRecordSelector;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

public class SelectorEntity extends Entity {
    private final TableRecordSelector selector;

    public SelectorEntity(TableRecordSelector selector) {
        super("selector");
        this.selector = selector;
    }

    public TableRecordSelector getSelector() {
        return selector;
    }

    @Override
    public TableRecordSelector getValue() {
        return selector;
    }

    @Override
    public String getExactTypeName() {
        return getType().getName();
    }

    @Override
    public String toRecordForm() {
        throw new TypeCanNotBeUsedInRecordsException(getType());
    }
}
