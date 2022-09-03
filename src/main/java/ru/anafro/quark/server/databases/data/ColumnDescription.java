package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.databases.ql.types.EntityType;

import java.util.ArrayList;

public class ColumnDescription {
    private final String name;
    private final EntityType type;
    private final ArrayList<ColumnModifier> modifiers;

    public ColumnDescription(String name, EntityType type, ArrayList<ColumnModifier> modifiers) {
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

    public ArrayList<ColumnModifier> getModifiers() {
        return modifiers;
    }
}
