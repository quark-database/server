package ru.anafro.quark.server.language.entities;

import ru.anafro.quark.server.database.data.ColumnDescription;
import ru.anafro.quark.server.language.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.language.entities.exceptions.TypeCanNotBeUsedInRecordsException;
import ru.anafro.quark.server.facade.Quark;

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
    public String format() {
        return new StringConstructorBuilder()
                .name(columnDescription.type().getName())
                .argument(new StringEntity(columnDescription.name()))
                .arguments(columnDescription.modifiers())
                .format();
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
        return columnDescription.name().compareTo(((ColumnEntity) entity).getColumnDescription().name());
    }

    @Override
    public int hashCode() {
        return Quark.stringHashingFunction().hash(toInstructionForm());
    }

    @Override
    public String toInstructionForm() {
        var builder = new StringConstructorBuilder();

        builder.name(columnDescription.type().getName());
        builder.argument(new StringEntity(columnDescription.name()));

        for (var modifierEntity : columnDescription.modifiers()) {
            builder.argument(new ColumnModifierEntity(modifierEntity.getModifier(), modifierEntity.getModifierArguments()));
        }

        return builder.build();
    }

    public ColumnDescription getColumnDescription() {
        return columnDescription;
    }
}
