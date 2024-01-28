package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

public class DefaultColumnModifier extends ColumnModifier {
    public DefaultColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "default", true);
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return true;
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        if (field.isEmpty()) {
            field.set(arguments.getEntity("default value"));
        }
    }
}
