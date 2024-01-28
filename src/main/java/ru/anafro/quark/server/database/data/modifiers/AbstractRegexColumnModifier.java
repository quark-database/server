package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

public abstract class AbstractRegexColumnModifier extends ColumnModifier {
    private final String regex;

    public AbstractRegexColumnModifier(String name, String regex) {
        super(ColumnModifierApplicationPriority.HIGH, name, false, "str");
        this.regex = regex;
    }


    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return field.getEntity().valueAs(String.class).matches(regex);
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        //
    }

}
