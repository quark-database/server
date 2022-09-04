package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class CopySignConstructor extends InstructionEntityConstructor {
    public CopySignConstructor() {
        super("copy sign", InstructionEntityConstructorParameter.required("magnitude", "float"), InstructionEntityConstructorParameter.required("sign", "float"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new FloatEntity(Math.copySign(arguments.<FloatEntity>get("magnitude").getValue(), arguments.<FloatEntity>get("sign").getValue()));
    }
}
