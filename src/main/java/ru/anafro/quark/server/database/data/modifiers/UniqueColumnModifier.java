package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents the {@code require unique} column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class UniqueColumnModifier extends ColumnModifier {
    public UniqueColumnModifier() {
        super(ColumnModifierApplicationPriority.LOW, "unique", false);
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var valueAlreadyExists = new AtomicBoolean(true);

        table.selectAll().forEach(record -> {
            if (record.getField(field.getColumnName()).getEntity().equals(field.getEntity())) {
                valueAlreadyExists.set(false);
            }
        });

        return valueAlreadyExists.get();
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Nothing.
    }

}
