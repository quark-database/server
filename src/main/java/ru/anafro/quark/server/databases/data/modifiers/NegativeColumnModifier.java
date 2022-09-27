package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

/**
 * Represents the {@code require negative} column modifier.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class NegativeColumnModifier extends ColumnModifier {
    public NegativeColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "require negative", false, "int", "float");
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        float value = field.getValue().hasType("float") ? field.getValue().valueAs(Float.class) : field.getValue().valueAs(Integer.class);
        return value < 0;
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
