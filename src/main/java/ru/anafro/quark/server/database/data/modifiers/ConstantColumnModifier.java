package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

/**
 * Represents the {@code require between} column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class ConstantColumnModifier extends ColumnModifier {
    public ConstantColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "require constant", false);
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // No value checks.
        return true;
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Nothing.
    }

}
