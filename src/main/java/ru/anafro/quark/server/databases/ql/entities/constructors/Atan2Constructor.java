package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class Atan2Constructor extends InstructionEntityConstructor {
    public Atan2Constructor() {
        super("atan two", InstructionEntityConstructorParameter.required("x", "float"), InstructionEntityConstructorParameter.required("y", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.atan2(arguments.<FloatEntity>get("y").getValue(), arguments.<FloatEntity>get("x").getValue()));
    }
}
