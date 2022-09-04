package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FloorConstructor extends InstructionEntityConstructor {
    public FloorConstructor() {
        super("floor", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.floor(arguments.<FloatEntity>get("number").getValue()));
    }
}
