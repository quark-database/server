package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class IsStringEmptyConstructor extends InstructionEntityConstructor {
    public IsStringEmptyConstructor() {
        super("is string empty", InstructionEntityConstructorParameter.required("string", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.<StringEntity>get("string").getString().isEmpty());
    }
}
