package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class StartsWithConstructor extends InstructionEntityConstructor {
    public StartsWithConstructor() {
        super("starts with", InstructionEntityConstructorParameter.required("string", "str"), InstructionEntityConstructorParameter.required("prefix", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        StringEntity string = arguments.get("string");
        StringEntity prefix = arguments.get("prefix");

        return new BooleanEntity(string.getString().startsWith(prefix.getString()));
    }
}
