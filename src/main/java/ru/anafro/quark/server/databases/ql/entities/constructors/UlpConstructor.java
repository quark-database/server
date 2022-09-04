package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class UlpConstructor extends InstructionEntityConstructor {
    public UlpConstructor() {
        super("ulp", InstructionEntityConstructorParameter.required("number", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.ulp(arguments.<FloatEntity>get("number").getValue()));
    }
}
