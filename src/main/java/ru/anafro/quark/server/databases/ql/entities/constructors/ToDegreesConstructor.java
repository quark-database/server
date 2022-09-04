package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ToDegreesConstructor extends InstructionEntityConstructor {
    public ToDegreesConstructor() {
        super("to degrees", InstructionEntityConstructorParameter.required("angle in radians", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.toDegrees(arguments.<FloatEntity>get("angle in radians").getValue()));
    }
}
