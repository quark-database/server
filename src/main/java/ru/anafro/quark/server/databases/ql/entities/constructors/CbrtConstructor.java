package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CbrtConstructor extends InstructionEntityConstructor {
    public CbrtConstructor() {
        super("cbrt", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.cbrt(arguments.<FloatEntity>get("number").getValue()));
    }
}
