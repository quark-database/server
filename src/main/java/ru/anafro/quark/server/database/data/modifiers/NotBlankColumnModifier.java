package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

public class NotBlankColumnModifier extends ColumnModifier {
    public NotBlankColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "not blank", false, "str");
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return !field.getEntity().valueAs(String.class).isBlank();
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        //
    }

}
