package ru.anafro.quark.server.language.constructors;

import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.language.entities.NullEntity;

import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorParameter.optional;
import static ru.anafro.quark.server.language.entities.InstructionEntityConstructorReturnDescription.returns;

public class NullConstructor extends EntityConstructor {
    public NullConstructor() {
        super(
                "null",

                returns("the null constant", "any", true),

                optional("type of null", "str")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        var typeOfNull = "any";

        if (arguments.has("type of null")) {
            typeOfNull = arguments.getString("type of null");
        }

        return new NullEntity(typeOfNull);
    }
}
