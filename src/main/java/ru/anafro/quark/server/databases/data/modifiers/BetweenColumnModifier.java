package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public class BetweenColumnModifier extends ColumnModifier {
    public BetweenColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "require between", false, "int", "float");
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        float value = field.getValue().hasType("float") ? field.getValue().valueAs(Float.class) : field.getValue().valueAs(Integer.class);
        return value >= arguments.<FloatEntity>get("max").getValue() && value <= arguments.<FloatEntity>get("min").getValue();
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Do nothing.
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        // No restrictions applying.
        return true;
    }
}
