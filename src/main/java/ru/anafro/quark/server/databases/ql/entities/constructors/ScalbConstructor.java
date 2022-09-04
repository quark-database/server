package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ScalbConstructor extends InstructionEntityConstructor {
    public ScalbConstructor() {
        super("scalb", InstructionEntityConstructorParameter.required("number", "float"), InstructionEntityConstructorParameter.required("scale factor", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        FloatEntity number = arguments.get("number");
        IntegerEntity scaleFactor = arguments.get("scale factor");

        return new FloatEntity(Math.scalb(number.getValue(), scaleFactor.getValue()));
    }
}
