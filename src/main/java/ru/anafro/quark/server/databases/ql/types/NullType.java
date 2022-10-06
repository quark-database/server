package ru.anafro.quark.server.databases.ql.types;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.NullEntity;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.databases.ql.entities.constructors.StringConstructorBuilder;

public class NullType extends EntityType {

    public NullType() {
        super("null", EntityType.CASTABLE_FROM_ANY_TYPE);
    }

    @Override
    public Entity makeEntity(String string) {
        Quark.logger().warning("NullType.makeEntity(String) is called. NullType is going to ignore the value '" + string + "' passed in and it will return null in any case.");

        return null;
    }

    @Override
    public String toInstructionForm(Entity entity) {
        if(entity.mismatchesType(this)) {
            Quark.logger().warning("NullType.toInstructionForm(String) is called. NullType is going to ignore the value '" + entity.toInstructionForm() + "' passed in and it will return @null(\"" + entity.getTypeName() + "\") in any case.");
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
