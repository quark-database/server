package ru.anafro.quark.server.databases.data;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class ColumnModifiersRegistry extends NamedObjectsRegistry<ColumnModifier> {

    @Override
    protected String getNameOf(ColumnModifier modifier) {
        return modifier.getName();
    }
}
