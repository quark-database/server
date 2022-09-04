package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FloorDivConstructor extends InstructionEntityConstructor {
    public FloorDivConstructor() {
        super("floor div", InstructionEntityConstructorParameter.required("first number", "int"), InstructionEntityConstructorParameter.required("second number", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.floorDiv(arguments.<IntegerEntity>get("first number").getValue(), arguments.<IntegerEntity>get("second number").getValue()));
    }
}
