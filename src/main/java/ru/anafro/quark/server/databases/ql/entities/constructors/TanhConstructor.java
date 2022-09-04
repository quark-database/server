package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class TanhConstructor extends InstructionEntityConstructor {
    public TanhConstructor() {
        super("tanh", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.tanh(arguments.<FloatEntity>get("number").getValue()));
    }
}
