package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class StringContainsConstructor extends InstructionEntityConstructor {
    public StringContainsConstructor() {
        super("string contains", InstructionEntityConstructorParameter.required("string where to search", "str"), InstructionEntityConstructorParameter.required("searching string", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.<StringEntity>get("string where to search").getString().contains(arguments.<StringEntity>get("searching string").getString()));
    }
}
