package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ReverseStringConstructor extends InstructionEntityConstructor {
    public ReverseStringConstructor() {
        super("reverse string", InstructionEntityConstructorParameter.required("string to reverse", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(new StringBuffer(arguments.<StringEntity>get("string to reverse").getString()).reverse().toString());
    }
}
