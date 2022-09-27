package ru.anafro.quark.server.databases.data.modifiers;

import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.databases.data.RecordField;
import ru.anafro.quark.server.databases.data.Table;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;

/**
 * Represents the {@code require between} column modifier.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public class BetweenColumnModifier extends ColumnModifier {

    /**
     * Creates a new column modifier instance.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public BetweenColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGHEST, "require between", false, "int", "float");
    }

    /**
     * Checks whether a field is accepted for this table or not.
     *
     * @param table a table the field is going to contain.
     * @param field a field.
     * @param arguments the arguments of the modifier.
     * @return {@code true} if field is acceptable.
     */
    @Override
    public boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        float value = field.getValue().hasType("float") ? field.getValue().valueAs(Float.class) : field.getValue().valueAs(Integer.class);
        return value >= arguments.<FloatEntity>get("max").getValue() && value <= arguments.<FloatEntity>get("min").getValue();
    }

    /**
     * Run before record with a field is inserted.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Do nothing.
    }

    /**
     * Checks whether column deletion is allowed.
     *
     * @since   Quark 1.1
     * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    @Override
    public boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments) {
        // No restrictions applying.
        return true;
    }
}
