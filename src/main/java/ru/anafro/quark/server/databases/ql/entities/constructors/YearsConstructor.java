package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class YearsConstructor extends EntityConstructor {
    public YearsConstructor() {
        super(
                "years",

                returns("the amount of milliseconds", "long"),

                required("years", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(arguments.getFloat("seconds") * 1000.0F * 3600.0F * 24 * 365);
    }
}
