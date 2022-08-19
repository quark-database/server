package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class EntityConstructorRegistry extends NamedObjectsRegistry<InstructionEntityConstructor> {

    @Override
    protected String getNameOf(InstructionEntityConstructor constructor) {
        return constructor.getName();
    }
}
