package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import java.util.concurrent.atomic.AtomicBoolean;

public class UniqueColumnModifier extends ColumnModifier {
    public UniqueColumnModifier() {
        super(ColumnModifierApplicationPriority.LOWEST, "require unique", false);
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var valueAlreadyExists = new AtomicBoolean(false);

        table.getRecords().forEach(record -> {
            if(record.getField(field.getColumnName()).getValue().getValue().equals(field.getValue())) {
                valueAlreadyExists.set(true);
            }
        });

        return valueAlreadyExists.get();
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Nothing.
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        return true;
    }
}