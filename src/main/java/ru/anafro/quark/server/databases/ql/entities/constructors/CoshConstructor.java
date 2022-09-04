package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CoshConstructor extends InstructionEntityConstructor {
    public CoshConstructor() {
        super("cosh", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.cosh(arguments.<FloatEntity>get("number").getValue()));
    }
}
