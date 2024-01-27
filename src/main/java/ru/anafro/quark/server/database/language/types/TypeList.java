package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class TypeList extends NamedObjectsList<EntityType<?>> {
    @Override
    protected String getNameOf(EntityType<?> type) {
        return type.getName();
    }
}
