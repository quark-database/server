package ru.anafro.quark.server.databases.ql.entities.constructors;


import ru.anafro.quark.server.databases.ql.entities.Entity;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.FloatEntity;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorArguments;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.ConstructorRuntimeException;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class ModConstructor extends EntityConstructor {

    public ModConstructor() {
        super(
                "mod",

                returns("remainder", "float"),

                required("dividend", "float"),
                required("divisor", "float")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        double dividend = arguments.getFloat("dividend");
        double divisor = arguments.getFloat("divisor");

        if(divisor == 0.0) {
            throw new ConstructorRuntimeException("Modulo with zero divisor is prohibited");
        }

        return new FloatEntity((float) (dividend % divisor));
    }
}
