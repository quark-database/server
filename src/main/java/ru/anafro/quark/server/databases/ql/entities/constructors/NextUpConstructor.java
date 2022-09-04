package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class NextUpConstructor extends InstructionEntityConstructor {
    public NextUpConstructor() {
        super("next up", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        FloatEntity number = arguments.get("number");

        return new FloatEntity(Math.nextUp(number.getValue()));
    }
}
