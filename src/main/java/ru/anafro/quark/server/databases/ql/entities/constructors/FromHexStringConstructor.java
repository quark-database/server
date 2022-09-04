package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FromHexStringConstructor extends InstructionEntityConstructor {
    public FromHexStringConstructor() {
        super("from hex string", InstructionEntityConstructorParameter.required("hex string to convert to integer", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(Integer.parseInt(arguments.<StringEntity>get("hex string to convert to integer").getString(), 16));
    }
}
