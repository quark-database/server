package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class RintConstructor extends InstructionEntityConstructor {
    public RintConstructor() {
        super("rint", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.rint(arguments.<FloatEntity>get("number").getValue()));
    }
}
