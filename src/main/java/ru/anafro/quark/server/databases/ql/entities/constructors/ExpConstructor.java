package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ExpConstructor extends InstructionEntityConstructor {
    public ExpConstructor() {
        super("exp", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.exp(arguments.<FloatEntity>get("number").getValue()));
    }
}
