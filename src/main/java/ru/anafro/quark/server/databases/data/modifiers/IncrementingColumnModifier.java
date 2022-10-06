package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.IntegerEntity;

/**
 * Represents the {@code incrementing} column modifier.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class IncrementingColumnModifier extends ColumnModifier {

    /**
     * The variable name that contains the last ID generated.
     * @since Quark 1.1
     */
    public static final String INCREMENTING_VARIABLE_NAME_FORMAT = "Last-Generated (%s)";

    /**
     * Creates a new column modifier instance.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public IncrementingColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "incrementing", true, "int");
    }

    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return true;
    }

    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        var lastValue = table.getVariableFolder().getVariable(INCREMENTING_VARIABLE_NAME_FORMAT.formatted(field.getColumnName()));
        field.setValue(lastValue.get(new IntegerEntity(1)));

        lastValue.<IntegerEntity>change(variableValueEntity -> new IntegerEntity(variableValueEntity.getValue() + 1), null /* 100% it's present. */);
    }

    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        return true;
    }
}
