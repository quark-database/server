package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FastHypotConstructor extends InstructionEntityConstructor {
    public FastHypotConstructor() {
        super("fast hypot", InstructionEntityConstructorParameter.required("first leg", "float"), InstructionEntityConstructorParameter.required("second leg", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.sqrt(Math.pow(arguments.<FloatEntity>get("first leg").getValue(), 2.0) + Math.pow(arguments.<FloatEntity>get("second leg").getValue(), 2.0)));
    }
}
