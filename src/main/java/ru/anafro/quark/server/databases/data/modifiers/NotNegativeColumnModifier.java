package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class NotNegativeColumnModifier extends ColumnModifier {
    public NotNegativeColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "require not negative", false, "int", "float");
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        float value = field.getValue().hasType("float") ? field.getValue().valueAs(Float.class) : field.getValue().valueAs(Integer.class);
        return value >= 0;
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Nothing.
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        return false;
    }
}
