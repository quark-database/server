package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class RightTrimConstructor extends InstructionEntityConstructor {
    public RightTrimConstructor() {
        super("right trim", InstructionEntityConstructorParameter.required("string to trim", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(arguments.<StringEntity>get("string to trim").getString().replaceAll("\\s+$", ""));
    }
}
