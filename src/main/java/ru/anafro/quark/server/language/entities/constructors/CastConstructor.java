package ru.anafro.quark.server.language.entities.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.language.entities.constructors.exceptions.ConstructorRuntimeException;
import ru.anafro.quark.server.facade.Quark;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class CastConstructor extends EntityConstructor {
    public CastConstructor() {
        super(
                "cast",
                returns("casted entity", "any"),
                required("casting entity", "?"),
                required("cast type", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var typeName = arguments.getString("cast type");
        var type = Quark.type(typeName);
        var entity = arguments.getEntity("casting entity");

        if (type == null) {
            throw new ConstructorRuntimeException(STR."The type \{typeName} is unknown.");
        }

        if (entity.hasType(type)) {
            return entity;
        }

        if (type.canCast(entity)) {
            return type.cast(entity);
        }

        throw new ConstructorRuntimeException(STR."Cannot cast \{entity} to \{type}.");
    }
}
