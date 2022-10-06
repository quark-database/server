package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.entities.ColumnModifierEntity;
import ru.anafro.quark.server.databases.ql.types.EntityType;

import java.util.ArrayList;

public class ColumnDescription {
    private final String name;
    private final EntityType type;
    private final ArrayList<ColumnModifierEntity> modifiers;

    public ColumnDescription(String name, EntityType type, ArrayList<ColumnModifierEntity> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }

    public ArrayList<ColumnModifierEntity> getModifiers() {
        return modifiers;
    }
}
