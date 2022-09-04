package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class UpperConstructor extends InstructionEntityConstructor {
    public UpperConstructor() {
        super("upper", InstructionEntityConstructorParameter.required("string to uppercase", "str"));
    }

    @Override
    public Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(arguments.<StringEntity>get("string to uppercase").getValue().toUpperCase());
    }
}
