package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ToBinaryStringConstructor extends InstructionEntityConstructor {
    public ToBinaryStringConstructor() {
        super("to binary string", InstructionEntityConstructorParameter.required("integer to convert to binary string", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(Integer.toBinaryString(arguments.<IntegerEntity>get("integer to convert to binary string").getValue()));
    }
}
