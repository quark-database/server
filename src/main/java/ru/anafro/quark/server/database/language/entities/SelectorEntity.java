package ru.anafro.quark.server.database.language.entities;

import ru.anafro.quark.server.database.data.ExpressionTableRecordSelector;
import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.database.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.facade.Quark;

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
    public String format() {
        return new StringConstructorBuilder().name(getTypeName()).argument(new StringEntity(selector.expression())).format();
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
