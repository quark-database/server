package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class LogConstructor extends InstructionEntityConstructor {
    public LogConstructor() {
        super("log", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.log(arguments.<FloatEntity>get("number").getValue()));
    }
}
