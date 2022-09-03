package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.databases.data.ColumnModifier;

public class ColumnModifierEntity extends Entity {
    private final String columnName;
    private final ColumnModifier modifier;

    public ColumnModifierEntity(ColumnModifier modifier) {
        super("modifier");
        this.modifier = modifier;
    }

    @Override
    public ColumnModifier getValue() {
        return modifier;
    }

    @Override
    public String getValueAsString() {
        return "<" + modifier.getName() + " " + this.getType() + ">";
    }
}
