package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class AsinConstructor extends InstructionEntityConstructor {
    public AsinConstructor() {
        super("asin", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity((float) Math.asin(arguments.<FloatEntity>get("number").getValue()));
    }
}
