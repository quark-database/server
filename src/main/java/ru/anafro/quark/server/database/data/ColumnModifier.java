package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.database.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.database.language.types.EntityType;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.utils.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a column modifier.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public abstract class ColumnModifier {
    public static final ArrayList<EntityType<?>> ANY_TYPE_ALLOWED = null;
    private final ColumnModifierApplicationPriority applicationPriority;
    private final ArrayList<EntityType<?>> allowedTypes;
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

    public static ColumnModifierEntity modifier(String modifierName, Object... constructorArguments) {
        var constructors = Quark.constructors();

        var constructor = constructors.getOrThrow(modifierName, STR."No such modifier \{modifierName}");

        if (constructor.doesntHaveReturnType("modifier")) {
            throw new QuarkException(STR."No such modifier '\{modifierName}'");
        }

        return (ColumnModifierEntity) constructor.eval(constructorArguments);
    }

    public ArrayList<EntityType<?>> getAllowedTypes() {
        return allowedTypes;
    }

    public String getName() {
        return name;
    }

    public ColumnModifierApplicationPriority getApplicationPriority() {
        return applicationPriority;
    }

    public boolean isGeneratingValues() {
        return valuesShouldBeGenerated;
    }

    public boolean isAnyTypeAllowed() {
        return allowedTypes == ANY_TYPE_ALLOWED;
    }

    public boolean isTypeAllowed(EntityType<?> type) {
        if (isAnyTypeAllowed()) {
            return true;
        }

        return allowedTypes.stream().anyMatch(type::equals);
    }

    public abstract boolean isFieldValid(Table table, RecordField field, InstructionEntityConstructorArguments arguments);

    public abstract void prepareField(Table table, RecordField field, InstructionEntityConstructorArguments arguments);

    @Override
    public String toString() {
        return STR."ColumnModifier{applicationPriority=\{applicationPriority}, allowedTypes=\{Lists.join(allowedTypes, EntityType::getName)}, valuesShouldBeGenerated=\{valuesShouldBeGenerated}, name='\{name}\{'\''}\{'}'}";
    }
}
