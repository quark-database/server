package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class FromBinaryStringConstructor extends InstructionEntityConstructor {
    public FromBinaryStringConstructor() {
        super("from binary string", InstructionEntityConstructorParameter.required("binary string to convert to integer", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(Integer.parseInt(arguments.<StringEntity>get("binary string to convert to integer").getString(), 2));
    }
}
