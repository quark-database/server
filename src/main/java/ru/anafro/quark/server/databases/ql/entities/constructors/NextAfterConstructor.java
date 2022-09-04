package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class NextAfterConstructor extends InstructionEntityConstructor {
    public NextAfterConstructor() {
        super("next after", InstructionEntityConstructorParameter.required("start", "float"), InstructionEntityConstructorParameter.required("direction", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        FloatEntity start = arguments.get("start");
        FloatEntity direction = arguments.get("direction");

        return new FloatEntity(Math.nextAfter(start.getValue(), direction.getValue()));
    }
}
