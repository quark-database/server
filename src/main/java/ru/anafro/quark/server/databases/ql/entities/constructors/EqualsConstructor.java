package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class EqualsConstructor extends InstructionEntityConstructor {
    public EqualsConstructor() {
        super("equals", InstructionEntityConstructorParameter.required("first object", "?"), InstructionEntityConstructorParameter.required("second object", "?"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.get("first object").getValue().equals(arguments.get("second object").getValue()));
    }
}
