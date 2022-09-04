package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class SignumConstructor extends InstructionEntityConstructor {
    public SignumConstructor() {
        super("signum", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.signum(arguments.<FloatEntity>get("number").getValue()));
    }
}
