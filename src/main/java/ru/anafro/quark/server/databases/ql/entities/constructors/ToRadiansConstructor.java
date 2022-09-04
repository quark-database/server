package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ToRadiansConstructor extends InstructionEntityConstructor {
    public ToRadiansConstructor() {
        super("to radians", InstructionEntityConstructorParameter.required("angle in degrees", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.toRadians(arguments.<FloatEntity>get("angle in degrees").getValue()));
    }
}
