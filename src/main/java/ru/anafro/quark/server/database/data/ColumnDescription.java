package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.database.language.types.EntityType;
import ru.anafro.quark.server.facade.Quark;

import java.util.List;
import java.util.Optional;

import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public record ColumnDescription(String name, EntityType<?> type, List<ColumnModifierEntity> modifiers) {

    public static ColumnDescription column(String columnName, String typeName, ColumnModifierEntity... modifiers) {
        return new ColumnDescription(columnName, Quark.type(typeName), list(modifiers));
    }

    public static ColumnDescription id() {
        return column(
                "id",
                "int",
                modifier("incrementing"),
                modifier("required"),
                modifier("positive"),
                modifier("unique")
        );
    }

    public String getTypeName() {
        return type.getName();
    }

    public Optional<ColumnModifierEntity> tryGetGeneratingModifier() {
        return modifiers.stream().filter(modifierEntity -> modifierEntity.getModifier().isGeneratingValues()).findFirst();
    }

    public boolean isGenerated() {
        return tryGetGeneratingModifier().isPresent();
    }

    public boolean isNotGenerated() {
        return !isGenerated();
    }

    public void addModifier(ColumnModifierEntity modifier) {
        modifiers.add(modifier);
    }

    public boolean hasModifier(String modifierName) {
        return modifiers.stream().anyMatch(entity -> entity.getModifier().getName().equals(modifierName));
    }
}
