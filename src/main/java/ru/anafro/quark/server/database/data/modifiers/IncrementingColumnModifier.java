package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.database.language.entities.IntegerEntity;

/**
 * Represents the {@code incrementing} column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class IncrementingColumnModifier extends ColumnModifier {

    /**
     * Creates a new column modifier instance.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public IncrementingColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "incrementing", true, "int");
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return true;
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var columnName = field.getColumnName();
        var nextInt = table.getVariable(STR."Last-Generated (\{columnName})");

        if (nextInt.isEmpty()) {
            nextInt.set(1);
        }

        field.set(nextInt.get().orElseThrow());
        nextInt.update(IntegerEntity::incremented);
    }
}
