package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

public abstract class AbstractRegexColumnModifier extends ColumnModifier {
    private final String regex;

    public AbstractRegexColumnModifier(String name, String regex) {
        super(ColumnModifierApplicationPriority.HIGHEST, name, false, "str");
        this.regex = regex;
    }


    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return false;
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        //
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        return false;
    }

    public String getRegex() {
        return regex;
    }
}
