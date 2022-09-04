package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class NextDownConstructor extends InstructionEntityConstructor {
    public NextDownConstructor() {
        super("next down", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        FloatEntity number = arguments.get("number");

        return new FloatEntity(Math.nextDown(number.getValue()));
    }
}
