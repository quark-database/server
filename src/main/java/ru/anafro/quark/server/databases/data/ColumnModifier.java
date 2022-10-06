package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.types.EntityType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a column modifier.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public abstract class ColumnModifier {
    public static final ArrayList<EntityType> ANY_TYPE_ALLOWED = null;
    private final ColumnModifierApplicationPriority applicationPriority;
    private final ArrayList<EntityType> allowedTypes;
    private final boolean valuesShouldBeGenerated;
    private final String name;

    public ColumnModifier(ColumnModifierApplicationPriority applicationPriority, String name, boolean valuesShouldBeGenerated, String... allowedTypes) {
        this.applicationPriority = applicationPriority;
        this.name = name;
        this.valuesShouldBeGenerated = valuesShouldBeGenerated;
        this.allowedTypes = allowedTypes.length == 0 ? ANY_TYPE_ALLOWED : new ArrayList<>(Arrays.stream(allowedTypes).map(Quark.types()::get).toList());
    }

    public ColumnModifier(ColumnModifierApplicationPriority applicationPriority, String name, boolean valuesShouldBeGenerated) {
        this.applicationPriority = applicationPriority;
        this.name = name;
        this.valuesShouldBeGenerated = valuesShouldBeGenerated;
        this.allowedTypes = ANY_TYPE_ALLOWED;
    }

    public ArrayList<EntityType> getAllowedTypes() {
        return allowedTypes;
    }

    public String getName() {
        return name;
    }

    public ColumnModifierApplicationPriority getApplicationPriority() {
        return applicationPriority;
    }

    public boolean areValuesShouldBeGenerated() {
        return valuesShouldBeGenerated;
    }

    public boolean isAnyTypeAllowed() {
        return allowedTypes == ANY_TYPE_ALLOWED;
    }

    public boolean isTypeAllowed(EntityType type) {
        if(isAnyTypeAllowed()) {
            return true;
        }

        return allowedTypes.stream().anyMatch(type::equals);
    }

    public abstract boolean isFieldSuitable(Table table, RecordField field, InstructionEntityConstructorArguments arguments);
    public abstract void beforeRecordInsertion(Table table, RecordField field, InstructionEntityConstructorArguments arguments);
    public abstract boolean isColumnDeletionAllowed(Table table, String columnName, InstructionEntityConstructorArguments arguments);
}
