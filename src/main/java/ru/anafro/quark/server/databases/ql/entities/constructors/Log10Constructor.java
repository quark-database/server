package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class Log10Constructor extends InstructionEntityConstructor {
    public Log10Constructor() {
        super("log ten", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.log10(arguments.<FloatEntity>get("number").getValue()));
    }
}
