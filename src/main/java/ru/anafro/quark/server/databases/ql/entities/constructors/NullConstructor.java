package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.optional;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

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

        if(arguments.has("type of null")) {
            typeOfNull = arguments.getString("type of null");
        }

        return new NullEntity(typeOfNull);
    }
}
