package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FloorModConstructor extends InstructionEntityConstructor {
    public FloorModConstructor() {
        super("floor mod", InstructionEntityConstructorParameter.required("first number", "int"), InstructionEntityConstructorParameter.required("second number", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.floorMod(arguments.<IntegerEntity>get("first number").getValue(), arguments.<IntegerEntity>get("second number").getValue()));
    }
}
