package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class IEEERemainderConstructor extends InstructionEntityConstructor {
    public IEEERemainderConstructor() {
        super("ieee remainder", InstructionEntityConstructorParameter.required("first number", "float"), InstructionEntityConstructorParameter.required("second number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.IEEEremainder(arguments.<FloatEntity>get("first value").getValue(), arguments.<FloatEntity>get("second value").getValue()));
    }
}
