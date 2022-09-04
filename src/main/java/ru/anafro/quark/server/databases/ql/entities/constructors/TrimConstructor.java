package ru.anafro.quark.server.databases.ql.entities.constructors;

import ru.anafro.quark.server.databases.ql.entities.*;

public class TrimConstructor extends InstructionEntityConstructor {
    public TrimConstructor() {
        super("trim", InstructionEntityConstructorParameter.required("string to trim", "str"));
    }

    @Override
    protected Entity invoke(InstructionEntityConstructorArguments arguments) {
        return new StringEntity(arguments.<StringEntity>get("string to trim").getString().trim());
    }
}
