package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class TypeRegistry extends NamedObjectsRegistry<EntityType> {
    @Override
    protected String getNameOf(EntityType type) {
        return type.getName();
    }
}
