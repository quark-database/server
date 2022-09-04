package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class AbsConstructor extends InstructionEntityConstructor {
    public AbsConstructor() {
        super("abs", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.abs(arguments.<FloatEntity>get("number").getValue()));
    }
}
