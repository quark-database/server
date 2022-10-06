package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents the {@code require unique} column modifier.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class RequireUniqueColumnModifier extends ColumnModifier {
    public RequireUniqueColumnModifier() {
        super(ColumnModifierApplicationPriority.LOWEST, "require unique", false);
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var valueAlreadyExists = new AtomicBoolean(true);

        table.getRecords().forEach(record -> {
            if(record.getField(field.getColumnName()).getValue().getValue().equals(field.getValue())) {
                valueAlreadyExists.set(false);
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
