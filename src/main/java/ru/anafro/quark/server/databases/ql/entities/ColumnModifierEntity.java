package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.databases.ql.entities.exceptions.TypeCanNotBeUsedInRecordsException;

import java.util.Objects;

public class ColumnModifierEntity extends Entity {
    private final String columnName;
    private final ColumnModifier modifier;
    private final InstructionEntityConstructorArguments modifierArguments;

    public ColumnModifierEntity(String columnName, ColumnModifier modifier, InstructionEntityConstructorArguments modifierArguments) {
        super("modifier");

        Objects.requireNonNull(modifier, "Modifier should be present when creating ColumnModifierEntity");

        this.columnName = columnName;
        this.modifier = modifier;
        this.modifierArguments = modifierArguments;
    }

    @Override
    public ColumnModifier getValue() {
        return modifier;
    }

    public ColumnModifier getModifier() {
        return modifier;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toInstructionForm() {
        return new StringConstructorBuilder()
                .name(modifier.getName())
                .argument(new StringEntity(columnName))
                .arguments(modifierArguments)
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

    public InstructionEntityConstructorArguments getModifierArguments() {
        return modifierArguments;
    }
}
