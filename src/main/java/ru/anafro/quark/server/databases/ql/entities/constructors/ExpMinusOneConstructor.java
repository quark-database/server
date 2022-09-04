package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ExpMinusOneConstructor extends InstructionEntityConstructor {
    public ExpMinusOneConstructor() {
        super("exp minus one", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.expm1(arguments.<FloatEntity>get("number").getValue()));
    }
}
