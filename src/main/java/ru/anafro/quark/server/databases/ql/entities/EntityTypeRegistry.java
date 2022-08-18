package ru.anafro.quark.server.databases.ql.entities;

import ru.anafro.quark.server.utils.patterns.NamedObjectsRegistry;

public class EntityTypeRegistry extends NamedObjectsRegistry<InstructionEntity> {

    @Override
    protected String getNameOfObject(InstructionEntity entity) {
        return entity.getType();
    }
}
