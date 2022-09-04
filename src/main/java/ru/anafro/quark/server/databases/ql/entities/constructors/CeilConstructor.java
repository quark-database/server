package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CeilConstructor extends InstructionEntityConstructor {
    public CeilConstructor() {
        super("ceil", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.ceil(arguments.<FloatEntity>get("number").getValue()));
    }
}
