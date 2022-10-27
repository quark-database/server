package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.data.ColumnDescription;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

public class ColumnEntity extends Entity {
    private final ColumnDescription columnDescription;

    public ColumnEntity(ColumnDescription columnDescription) {
        super("column");
        this.columnDescription = columnDescription;
    }

    @Override
    public ColumnDescription getValue() {
        return columnDescription;
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
        return columnDescription.getName().compareTo(((ColumnEntity) entity).getColumnDescription().getName());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(toInstructionForm());
    }

    @Override
    public String toInstructionForm() {
        var builder = new StringConstructorBuilder();

        builder.name(columnDescription.getType().getName());
        builder.argument(new StringEntity(columnDescription.getName()));

        for(var modifierEntity : columnDescription.getModifiers()) {
            builder.argument(new ColumnModifierEntity(columnDescription.getName(), modifierEntity.getModifier(), modifierEntity.getModifierArguments()));
        }

        return builder.build();
    }

    public ColumnDescription getColumnDescription() {
        return columnDescription;
    }
}
