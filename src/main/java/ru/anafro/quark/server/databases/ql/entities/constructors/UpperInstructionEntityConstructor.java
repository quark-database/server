package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class UpperInstructionEntityConstructor extends InstructionEntityConstructor {
    public UpperInstructionEntityConstructor() {
        super("upper", InstructionEntityConstructorParameter.required("string to uppercase", "str"));
    }

    @Override
    public InstructionEntity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(arguments.<StringEntity>get("string to uppercase").getValue().toUpperCase());
    }
}
