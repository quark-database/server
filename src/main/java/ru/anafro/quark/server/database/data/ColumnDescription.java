package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.database.data.exceptions.ModifierExistsException;
import ru.anafro.quark.server.facade.Quark;
import ru.anafro.quark.server.language.entities.ColumnModifierEntity;
import ru.anafro.quark.server.language.types.EntityType;
import ru.anafro.quark.server.utils.collections.Collections;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.anafro.quark.server.database.data.ColumnModifier.modifier;
import static ru.anafro.quark.server.utils.collections.Collections.list;

public final class ColumnDescription {
    private final EntityType<?> type;
    private final List<ColumnModifierEntity> modifiers;
    private String name;

    public ColumnDescription(String name, EntityType<?> type, List<ColumnModifierEntity> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

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
        if (hasModifier(modifier.getModifier().getName())) {
            throw new ModifierExistsException(this, modifier);
        }

        modifiers.add(modifier);
    }

    public boolean hasModifier(String modifierName) {
        return modifiers.stream().anyMatch(entity -> entity.getModifier().getName().equals(modifierName));
    }

    public boolean doesntHaveModifier(String modifier) {
        return !hasModifier(modifier);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String name() {
        return name;
    }

    public EntityType<?> type() {
        return type;
    }

    public List<ColumnModifierEntity> modifiers() {
        return modifiers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ColumnDescription) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type) &&
                Collections.equalsIgnoreOrder(this.modifiers, that.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, modifiers);
    }

    @Override
    public String toString() {
        return "ColumnDescription[" +
                "name=" + name + ", " +
                "type=" + type + ", " +
                "modifiers=" + modifiers + ']';
    }
}
