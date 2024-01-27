package ru.anafro.quark.server.database.data;

import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class ColumnModifierList extends NamedObjectsList<ColumnModifier> {

    @Override
    protected String getNameOf(ColumnModifier modifier) {
        return modifier.getName();
    }
}
