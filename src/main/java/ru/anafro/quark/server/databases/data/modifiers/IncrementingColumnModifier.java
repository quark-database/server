package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;

public class IncrementingColumnModifier extends ColumnModifier {

    public static final String INCREMENTING_VARIABLE_NAME_FORMAT = "Last-Generated (%s)";

    public IncrementingColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "incrementing", true, "int");
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return true;
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var lastValue = table.getVariableFolder().getVariable(INCREMENTING_VARIABLE_NAME_FORMAT.formatted(field.getColumnName()));
        field.setValue(lastValue.get());
        lastValue.<IntegerEntity>change(variableValueEntity -> new IntegerEntity(variableValueEntity.getValue() + 1));
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        return true;
    }
}
