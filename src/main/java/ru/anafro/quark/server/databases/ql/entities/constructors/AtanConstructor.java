package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class AtanConstructor extends InstructionEntityConstructor {
    public AtanConstructor() {
        super("atan", InstructionEntityConstructorParameter.required("atan", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.atan(arguments.<FloatEntity>get("number").getValue()));
    }
}
