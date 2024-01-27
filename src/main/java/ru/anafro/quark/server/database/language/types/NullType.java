package ru.anafro.quark.server.database.language.types;

import ru.anafro.quark.server.database.language.entities.Entity;
import ru.anafro.quark.server.database.language.entities.NullEntity;
import ru.anafro.quark.server.database.language.entities.StringEntity;
import ru.anafro.quark.server.database.language.entities.constructors.StringConstructorBuilder;
import ru.anafro.quark.server.facade.Quark;

public class NullType extends EntityType<NullEntity> {

    public NullType() {
        super("null", null, NullEntity.class);
    }

    @Override
    public NullEntity wrap(Object object) {
        return new NullEntity();
    }

    @Override
    public NullEntity makeEntity(String string) {
        Quark.logger().warning(STR."NullType.makeEntity(String) is called. NullType is going to ignore the value '\{string}' passed in and it will return null in any case.");

        return new NullEntity("any");
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if (entity.doesntHaveType(this)) {
            Quark.logger().warning(STR."NullType.toInstructionForm(String) is called. NullType is going to ignore the value '\{entity.toInstructionForm()}' passed in and it will return @null(\"\{entity.getTypeName()}\") in any case.");
        }

        return new StringConstructorBuilder()
                .name("null")
                .argument(new StringEntity(entity.getTypeName()))
                .build();
    }

    @Override
    protected Entity castOrNull(Entity entity) {
        return new NullEntity(entity.getTypeName());
    }
}
