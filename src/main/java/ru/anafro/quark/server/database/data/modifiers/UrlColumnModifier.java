package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

import java.net.URI;

public class UrlColumnModifier extends ColumnModifier {
    public UrlColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "url", false, "str");
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        try {
            // TODO: Replace with regular expression.
            //       return field.getEntity().valueAs(String.class).matches("???");

            var ignored = new URI(field.getEntity().valueAs(String.class)).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        //
    }

}
