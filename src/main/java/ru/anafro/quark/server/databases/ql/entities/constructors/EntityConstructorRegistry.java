package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;
import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class EntityConstructorRegistry extends NamedObjectsRegistry<EntityConstructor> {

    @Override
    protected String getNameOf(EntityConstructor constructor) {
        return constructor.getName();
    }
}
