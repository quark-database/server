package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class RoundConstructor extends InstructionEntityConstructor {
    public RoundConstructor() {
        super("round", InstructionEntityConstructorParameter.required("number to round", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.round(arguments.<FloatEntity>get("number to round").getValue()));
    }
}
