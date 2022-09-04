package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class MatchesConstructor extends InstructionEntityConstructor {
    public MatchesConstructor() {
        super("matches", InstructionEntityConstructorParameter.required("string to check", "str"), InstructionEntityConstructorParameter.required("regex expression", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new BooleanEntity(arguments.<StringEntity>get("string to check").getString().matches(arguments.<StringEntity>get("regex expression").getString()));
    }
}
