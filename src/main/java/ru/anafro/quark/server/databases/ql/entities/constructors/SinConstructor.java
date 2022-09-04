package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class SinConstructor extends InstructionEntityConstructor {
    public SinConstructor() {
        super("sin", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.sin(arguments.<FloatEntity>get("number").getValue()));
    }
}
