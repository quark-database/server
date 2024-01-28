package ru.anafro.quark.server.database.data.modifiers;

import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierApplicationPriority;
import ru.anafro.quark.server.database.data.RecordField;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;

/**
 * Represents the {@code require positive} column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public class PositiveColumnModifier extends ColumnModifier {
    public PositiveColumnModifier() {
        super(ColumnModifierApplicationPriority.HIGH, "positive", false, "int", "float", "long", "double");
    }

    @Override
    public boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        return switch (field.getEntity().getExactTypeName()) {
            case "int" -> field.getEntity().valueAs(Integer.class);
            case "float" -> field.getEntity().valueAs(Float.class);
            case "long" -> field.getEntity().valueAs(Long.class);
            case "double" -> field.getEntity().valueAs(Double.class);

            default -> throw new UnsupportedOperationException("null");  // Is never thrown.
        } > 0;
    }

    @Override
    public void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments) {
        // Nothing.
    }

}
