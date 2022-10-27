package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

public class SelectorEntity extends Entity {
    private final ExpressionTableRecordSelector selector;

    public SelectorEntity(ExpressionTableRecordSelector selector) {
        super("selector");
        this.selector = selector;
    }

    public ExpressionTableRecordSelector getSelector() {
        return selector;
    }

    @Override
    public ExpressionTableRecordSelector getValue() {
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

    @Override
    public int rawCompare(Entity entity) {
        return selector.getName().compareTo(((SelectorEntity) entity).getSelector().getName());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(selector.expression());
    }
}
