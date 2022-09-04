package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class ToHexStringConstructor extends InstructionEntityConstructor {
    public ToHexStringConstructor() {
        super("to hex string", InstructionEntityConstructorParameter.required("integer to convert to hex string", "int"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(Integer.toHexString(arguments.<IntegerEntity>get("integer to convert to hex string").getValue()));
    }
}
