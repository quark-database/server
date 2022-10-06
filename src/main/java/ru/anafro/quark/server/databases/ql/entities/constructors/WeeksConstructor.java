package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class WeeksConstructor extends EntityConstructor {
    public WeeksConstructor() {
        super(
                "weeks",

                returns("the amount of milliseconds", "long"),

                required("weeks", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(arguments.getFloat("seconds") * 1000.0F * 3600 * 24 * 7);
    }
}
