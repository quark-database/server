package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class GetExponentConstructor extends InstructionEntityConstructor {
    public GetExponentConstructor() {
        super("get exponent", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.getExponent(arguments.<FloatEntity>get("get exponent").getValue()));
    }
}
