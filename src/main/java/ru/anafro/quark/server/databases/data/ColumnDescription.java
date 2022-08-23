package ru.anafro.quark.server.databases.data;

import java.util.ArrayList;

public class ColumnDescription {
    private final String name;
    private final String type;
    private final ArrayList<ColumnModifier> modifiers;

    public ColumnDescription(String name, String type, ArrayList<ColumnModifier> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ArrayList<ColumnModifier> getModifiers() {
        return modifiers;
    }
}
