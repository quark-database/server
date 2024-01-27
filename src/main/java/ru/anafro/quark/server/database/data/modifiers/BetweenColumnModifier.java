package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;

/**
 * Represents the {@code require between} column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class BetweenColumnModifier extends ColumnModifier {

    /**
     * Creates a new column modifier instance.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public BetweenColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "require between", false, "int", "float");
    }

    /**
     * Checks whether a field is accepted for this table or not.
     *
     * @param table     a table the field is going to contain.
     * @param field     a field.
     * @param arguments the arguments of the modifier.
     * @return {@code true} if field is acceptable.
     */
    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        float value = field.getEntity().hasType("float") ? field.getEntity().valueAs(Float.class) : field.getEntity().valueAs(Integer.class);
        return value >= arguments.getFloat("max") && value <= arguments.getFloat("min");
    }

    /**
     * Run before record with a field is inserted.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Do nothing.
    }

}
