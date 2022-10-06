package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class SecondsConstructor extends EntityConstructor {
    public SecondsConstructor() {
        super(
                "seconds",

                returns("the amount of milliseconds", "long"),

                required("seconds", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(arguments.getFloat("seconds") * 1000.0F);
    }
}
