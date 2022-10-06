package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class MinutesConstructor extends EntityConstructor {
    public MinutesConstructor() {
        super(
                "minutes",

                returns("the amount of milliseconds", "long"),

                required("minutes", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(arguments.getFloat("minutes") * 60_000.0F);
    }
}
