package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.exceptions.ConstructorRuntimeException;

import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.optional;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorParameter.required;
import static ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructorReturnDescription.returns;

public class PowConstructor extends EntityConstructor {
    public PowConstructor() {
        super(
            "pow",

            returns("the power", "float"),

            required("base", "float"),
            required("exponent", "float"),
            optional("modulus", "int")
        );
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        float base = arguments.getFloat("base");
        float exponent = arguments.getFloat("exponent");

        float result = (float) Math.pow(base, exponent);

        if(arguments.has("modulus")) {
            int modulus = arguments.getInt("modulus");

            if(modulus == 0) {
                throw new ConstructorRuntimeException("Modulo by zero is prohibited.");
            } else {
                result %= modulus;
            }
        }

        return new FloatEntity(result);
    }
}
