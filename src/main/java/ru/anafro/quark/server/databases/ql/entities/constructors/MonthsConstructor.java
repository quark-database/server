package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class MonthsConstructor extends EntityConstructor {

    public MonthsConstructor() {
        super(
                "months",

                returns("the amount of milliseconds", "long"),

                required("months", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(arguments.getFloat("months") * 1000.0F * 3600.0F * 24 * 30);
    }
}
