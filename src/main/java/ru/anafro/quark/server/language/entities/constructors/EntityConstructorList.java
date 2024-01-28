package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.constructors.mapping.ConstructorClassMapper;
import ru.anafro.quark.server.utils.patterns.NamedObjectsList;

public class EntityConstructorList extends NamedObjectsList<EntityConstructor> {

    @Override
    protected String getNameOf(EntityConstructor constructor) {
        return constructor.getName();
    }

    public void supplement(Class<?>... types) {
        for (var type : types) {
            super.supplement(ConstructorClassMapper.map(type));
        }
    }
}
