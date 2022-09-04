package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class TanConstructor extends InstructionEntityConstructor {
    public TanConstructor() {
        super("tan", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.tan(arguments.<FloatEntity>get("number").getValue()));
    }
}
