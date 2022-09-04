package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class Log1PConstructor extends InstructionEntityConstructor {
    public Log1PConstructor() {
        super("log one p", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.log1p(arguments.<FloatEntity>get("number").getValue()));
    }
}
