package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class LengthConstructor extends InstructionEntityConstructor {
    public LengthConstructor() {
        super("length", InstructionEntityConstructorParameter.required("string to count characters in", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new IntegerEntity(arguments.<StringEntity>get("string to count characters in").getString().length());
    }
}
