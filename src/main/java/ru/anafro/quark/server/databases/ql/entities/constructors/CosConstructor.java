package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CosConstructor extends InstructionEntityConstructor {
    public CosConstructor() {
        super("cos", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.cos(arguments.<FloatEntity>get("number").getValue()));
    }
}
